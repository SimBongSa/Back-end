package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.request.BoardRequest;
import com.simbongsa.Backend.dto.response.*;
import com.simbongsa.Backend.entity.Board;
import com.simbongsa.Backend.entity.Comment;
import com.simbongsa.Backend.entity.Likes;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.repository.BoardRepository;
import com.simbongsa.Backend.repository.CommentRepository;
import com.simbongsa.Backend.repository.LikesRepository;
import com.simbongsa.Backend.util.Check;
import com.simbongsa.Backend.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;

    private final S3Uploader s3Uploader;
    private final Check check;

    /**
     * 게시물 생성 (관리자만 생성 가능)
     */
    public ResponseDto<MsgResponse> createBoard(Member member, BoardRequest boardRequest) throws IOException {
        // 관리자인지 확인
        check.isAdmin(member);
        String boardImage = s3Uploader.uploadFiles(boardRequest.getBoardImage(), "board");

        Board board = new Board(boardRequest, member, boardImage);
        boardRepository.save(board);

        return ResponseDto.success(new MsgResponse("게시물 생성 완료"));
    }

    /**
     * 게시물 전체 조회
     */
    public ResponseDto<List<BoardResponse>> getAllBoards() {
        List<Board> boards = boardRepository.findAll();
        List<BoardResponse> boardResponses = new ArrayList<>();

        for (Board board : boards) {
            boardResponses.add(new BoardResponse(board));
        }
        return ResponseDto.success(boardResponses);
    }

    /**
     * 게시물 날짜별 조회
     */
    public ResponseDto<List<BoardResponse>> getBoardsByDueDay(LocalDate dueDay) {
        // Todo 시간 관련 함수, 쿼리 공부
        List<Board> boards = boardRepository.findAllByDueDay(dueDay);

        List<BoardResponse> boardResponses = new ArrayList<>();
        for (Board board : boards) {
            boardResponses.add(new BoardResponse(board));
        }

        return ResponseDto.success(boardResponses);
    }

    /**
     * 게시물 상세 조회
     * 댓글 전체 조회
     * 조회수 증가
     */
    @Transactional
    public ResponseDto<BoardDetailResponse> getBoard(Long boardId) {
        // 게시물 존재 유무
        Board board = check.existBoard(boardId);

        // 조회수 증가
        board.addHits();

        List<Comment> comments = commentRepository.findAllByBoard(board);
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponses.add(new CommentResponse(comment));
        }

        return ResponseDto.success(new BoardDetailResponse(board, commentResponses));
    }

    /**
     * 게시물 수정
     * (작성한 관리자만 수정 가능)
     */
    @Transactional
    public ResponseDto<BoardUpdateResponse> updateBoard(Member member, BoardRequest boardRequest, Long boardId) throws IOException {
        // 게시물 존재 유무
        Board board = check.existBoard(boardId);
        // 작성자인지 확인
        check.isAuthor(member, board);

        String boardImage = s3Uploader.uploadFiles(boardRequest.getBoardImage(), "board");
        board.update(boardRequest, boardImage);

        return ResponseDto.success(new BoardUpdateResponse(board));
    }

    /**
     * 게시물 삭제
     * 작성자만 삭제 가능
     * 봉사 지원자 있을 경우 삭제 불가
     */
    @Transactional
    public ResponseDto<MsgResponse> deleteBoard(Member member, Long boardId) {
        Board board = check.existBoard(boardId);

        check.isAuthor(member, board);

        // 지원자 있는지 확인
        check.existApplicant(board);

        // 댓글 테이블 삭제 어노테이션 찾아보기
        boardRepository.delete(board);
        return ResponseDto.success(new MsgResponse("게시물 삭제 완료"));
    }

    /**
     * 게시물 찜 or 찜 취소
     */
    @Transactional
    public ResponseDto<MsgResponse> likeBoard(String username, Long boardId) {
        // 게시물 리스펀스에 찜 유무 리턴해야함 !!!!

        Likes likes = likesRepository.findByUsernameAndBoardId(username, boardId).orElse(null);
        String msg = "";
        // 찜한 기록이 없을 경우 -> 찜
        if (likes == null) {
            likesRepository.save(new Likes(username, boardId));
            msg = "찜!";
        }
        // 이미 찜한 경우 -> 찜 취소
        else {
            likesRepository.delete(likes);
            msg = "찜 취소";
        }

        return ResponseDto.success(new MsgResponse(msg));
    }

}


