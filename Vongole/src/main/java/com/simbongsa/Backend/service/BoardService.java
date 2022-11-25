package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.request.BoardRequest;
import com.simbongsa.Backend.dto.response.*;
import com.simbongsa.Backend.entity.*;
import com.simbongsa.Backend.repository.BoardRepository;
import com.simbongsa.Backend.repository.CommentRepository;
import com.simbongsa.Backend.repository.HashtagRepository;
import com.simbongsa.Backend.repository.LikesRepository;
import com.simbongsa.Backend.util.Check;
import com.simbongsa.Backend.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;
    private final HashtagRepository hashtagRepository;

    private final S3Uploader s3Uploader;
    private final Check check;

    /**
     * 게시물 생성 (관리자만 생성 가능)
     */
    public ResponseDto<BoardCreateResponse> createBoard(Member member, BoardRequest boardRequest) throws IOException {
        // 관리자인지 확인
        check.isAdmin(member);
        String boardImage = (boardRequest.getBoardImage().getOriginalFilename().equals("")) ?
                null : s3Uploader.uploadFiles(boardRequest.getBoardImage(), "board");

        Board board = new Board(boardRequest, member, boardImage);
        boardRepository.save(board);

        // 해시테그 객체 생성
        Long boardId = board.getId();
        List<Tag> tags = boardRequest.getTags();
        for (Tag tag : tags) {
            hashtagRepository.save(new Hashtag(boardId, tag.getMsg()));
        }

        return ResponseDto.success(new BoardCreateResponse(board.getId(), "게시물 생성 완료"));
    }

    /**
     * 게시물 전체 조회
     */
    public ResponseDto<List<BoardResponse>> getAllBoards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Board> boards = boardRepository.findAllByOrderByEndDateDesc(pageable);

        return getBoardResponses(boards);
    }

    /**
     * 게시물 월별 조회
     */
    public ResponseDto<List<BoardResponse>> getBoardsByMonth(String month) {
        LocalDate start = LocalDate.parse(month + "-01", DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate end = LocalDate.parse(month + "-30", DateTimeFormatter.ISO_LOCAL_DATE);

        List<Board> boards = boardRepository.findAllByDueDayBetween(start, end);
        List<BoardResponse> boardResponses = new ArrayList<>();
        for (Board board : boards) {
            boardResponses.add(new BoardResponse(board));
        }
        return ResponseDto.success(boardResponses);
    }

    /**
     * 게시물 날짜별 조회
     */
    public ResponseDto<List<BoardResponse>> getBoardsByDueDay(LocalDate dueDay, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Board> boards = boardRepository.findAllByDueDay(dueDay, pageable);

        return getBoardResponses(boards);
    }

    /**
     * boardResponse 생성 api
     */
    private ResponseDto<List<BoardResponse>> getBoardResponses(List<Board> boards) {
        List<BoardResponse> boardResponses = new ArrayList<>();
        for (Board board : boards) {

            List<Hashtag> hashtags = hashtagRepository.findAllByBoardId(board.getId());
            List<String> tags = new ArrayList<>();
            for (Hashtag hashtag : hashtags) {
                tags.add(hashtag.getTag());
            }

            boardResponses.add(new BoardResponse(board, tags));
        }
        return ResponseDto.success(boardResponses);
    }

    /**
     * 게시물 상세 조회
     * 댓글 전체 조회
     * 조회수 증가
     */
    @Transactional
    public ResponseDto<BoardDetailResponse> getBoard(Long boardId, int page, int size) {
        // 게시물 존재 유무
        Board board = check.existBoard(boardId);
        Pageable pageable = PageRequest.of(page, size);

        // 조회수 증가
        board.addHits();

        List<Hashtag> hashtags = hashtagRepository.findAllByBoardId(boardId);
        List<String> tags = new ArrayList<>();

        for (Hashtag hashtag : hashtags) {
            tags.add(hashtag.getTag());
        }

        List<Comment> comments = commentRepository.findAllByBoard(board, pageable);
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponses.add(new CommentResponse(comment));
        }

        return ResponseDto.success(new BoardDetailResponse(board, commentResponses, tags));
    }

    /**
     * 게시물 해시태그 별 조회
     */
    public ResponseDto<List<BoardResponse>> getBoardsByHashtag(Tag tag, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<Hashtag> hashtags = hashtagRepository.findAllByTag(tag);
        List<BoardResponse> boardResponses = new ArrayList<>();

        for (Hashtag hashtag : hashtags) {
            System.out.println("hi");
            Board board = boardRepository.findById(hashtag.getBoardId()).get();
            boardResponses.add(new BoardResponse(board));
        }
        return ResponseDto.success(boardResponses);
    }

    /**
     * 게시물 수정
     * (작성한 관리자만 수정 가능)
     */
    @Transactional
    public ResponseDto<MsgResponse> updateBoard(Member member, BoardRequest boardRequest, Long boardId) throws IOException {
        // 게시물 존재 유무
        Board board = check.existBoard(boardId);
        // 작성자인지 확인
        check.isAuthor(member, board);

        String boardImage = (boardRequest.getBoardImage().getOriginalFilename().equals("")) ?
                null : s3Uploader.uploadFiles(boardRequest.getBoardImage(), "board");
        board.update(boardRequest, boardImage);

        // TODO : 뭐야 hashtag 수정 어떻게 해...?

        return ResponseDto.success(new MsgResponse("수정 완료!"));
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

        // 좋아요 기록 삭제
        likesRepository.deleteAllByBoardId(boardId);

        // 해시태그 기록 삭제
        hashtagRepository.deleteAllByBoardId(boardId);

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
        String msg;
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


