package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.request.BoardRequest;
import com.simbongsa.Backend.dto.response.BoardDetailResponse;
import com.simbongsa.Backend.dto.response.BoardResponse;
import com.simbongsa.Backend.dto.response.MsgResponse;
import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.entity.Board;
import com.simbongsa.Backend.entity.Like;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.repository.BoardRepository;
import com.simbongsa.Backend.repository.LikeRepository;
import com.simbongsa.Backend.util.Check;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;

    private final Check check;

    /**
     * 게시물 생성 (관리자만 생성 가능)
     *
     * @param member
     * @param boardRequest
     * @param multipartFile
     */
    public ResponseDto<MsgResponse> createBoard(Member member, BoardRequest boardRequest, MultipartFile multipartFile) {
        // 관리자인지 확인

        // entity 객체 생성 후 db에 저장
        String boardImage = "";
        Board board = new Board(boardRequest, member, boardImage);
        boardRepository.save(board);

        return ResponseDto.success(new MsgResponse("게시물 생성 완료"));
    }

    /**
     * 게시물 전체 조회
     */
    public ResponseDto<List<BoardResponse>> getBoards(String dueDay) {
        List<Board> boards = boardRepository.findAllByDueDay(dueDay);

        List<BoardResponse> boardResponses = new ArrayList<>();
        for (Board board : boards) {
            boardResponses.add(new BoardResponse(board));
        }

        return ResponseDto.success(boardResponses);
    }

    /**
     * 게시물 수정
     * (작성한 관리자만 수정 가능)
     *
     * @param member
     * @param boardRequest
     * @param multipartFile
     * @param boardId
     * @return
     */
    @Transactional
    public ResponseDto<BoardDetailResponse> updateBoard(Member member, BoardRequest boardRequest, MultipartFile multipartFile, Long boardId) {
        Board board = check.isExist(boardId);

        check.isAuthor(member);

        String boardImage = "";
        board.update(boardRequest, boardImage);

        return ResponseDto.success(new BoardDetailResponse(board));
    }

    /**
     * 게시물 상세 조회
     * 댓글 전체 조회
     * 조회수 증가
     *
     * @param boardId
     * @return
     */
    @Transactional
    public ResponseDto<BoardDetailResponse> getBoard(Long boardId) {
        Board board = check.isExist(boardId);

        // 조회수 증가
        board.addHits();

        // 댓글 추가해야 함
        return ResponseDto.success(new BoardDetailResponse(board));
    }

    /**
     * 게시물 삭제
     * 작성자만 삭제 가능
     * 봉사 지원자 있을 경우 삭제 불가
     *
     * @param member
     * @param boardId
     * @return
     */
    @Transactional
    public ResponseDto<MsgResponse> deleteBoard(Member member, Long boardId) {
        Board board = check.isExist(boardId);

        check.isAuthor(member);

        // 지원자 있는지 확인해야 함

        boardRepository.delete(board);
        return ResponseDto.success(new MsgResponse("게시물 삭제 완료"));
    }

    /**
     * 게시물 찜 or 찜 취소
     *
     * @param username
     * @param boardId
     * @return
     */
    @Transactional
    public ResponseDto<MsgResponse> likeBoard(String username, Long boardId) {
        // 게시물 리스펀스에 찜 유무 리턴해야함 !!!!

        Like like = likeRepository.findByUsernameAndBoardId(username, boardId).orElse(null);
        String msg = "";
        // 찜한 기록이 없을 경우 -> 찜
        if (like == null) {
            likeRepository.save(new Like(username, boardId));
            msg = "찜!";
        }
        // 이미 찜한 경우 -> 찜 취소
        else {
            likeRepository.delete(like);
            msg = "찜 취소";
        }

        return ResponseDto.success(new MsgResponse(msg));
    }
}


