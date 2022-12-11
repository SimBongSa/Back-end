package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.request.BoardRequest;
import com.simbongsa.Backend.dto.request.BoardUpdateRequest;
import com.simbongsa.Backend.dto.response.*;
import com.simbongsa.Backend.entity.*;
import com.simbongsa.Backend.repository.*;
import com.simbongsa.Backend.util.Check;
import com.simbongsa.Backend.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;
    private final HashtagRepository hashtagRepository;
    private final EnrollRepository enrollRepository;

    private final S3Uploader s3Uploader;
    private final Check check;

    /**
     * 게시물 생성 (관리자만 생성 가능)
     */
    @Transactional
    public ResponseDto<BoardCreateResponse> createBoard(Member member, BoardRequest boardRequest) throws IOException {
        // 관리자인지 확인
        check.isAdmin(member);
        String boardImage = (boardRequest.getBoardImage().getOriginalFilename().equals("")) ?
                null : s3Uploader.uploadFiles(boardRequest.getBoardImage(), "board");

        Board board = new Board(boardRequest, member, boardImage);
        boardRepository.save(board);

        // 해시테그 객체 생성
        createHashtag(board.getId(), boardRequest.getTags());

        return ResponseDto.success(new BoardCreateResponse(board.getId(), "게시물 생성 완료"));
    }

    private void createHashtag(Long boardId, List<Tag> tags) {
        tags.forEach(tag -> hashtagRepository.save(new Hashtag(boardId, tag)));
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
    public ResponseDto<List<BoardDueDayResponse>> getBoardsByMonth(String year, String month) {

        List<Board> boards = boardRepository.findAllByDueDay(year,month);
        List<BoardDueDayResponse> boardDueDayResponses = new ArrayList<>();

        boards.forEach(board -> boardDueDayResponses.add(new BoardDueDayResponse(board)));

        return ResponseDto.success(boardDueDayResponses);
    }

    /**
     * 게시물 날짜별 조회
     */
    public ResponseDto<List<BoardResponse>> getBoardsByDueDay(LocalDate dueDay, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Timestamp start = Timestamp.valueOf(dueDay.atTime(0, 0, 0));
        Timestamp end = Timestamp.valueOf(dueDay.atTime(23, 59, 59));
        List<Board> boards = boardRepository.findAllByDueDayBetween(start, end, pageable);

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
            hashtags.forEach(hashtag -> tags.add(hashtag.getTag().getMsg()));

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
        hashtags.forEach(hashtag -> tags.add(hashtag.getTag().getMsg()));

        List<Comment> comments = commentRepository.findAllByBoardOrderByCreatedAtDesc(board, pageable);
        List<CommentResponse> commentResponses = new ArrayList<>();
        comments.forEach(comment -> commentResponses.add(new CommentResponse(comment)));

        List<String> applicants = new ArrayList<>();
        enrollRepository.findAllByBoard(board)
                .forEach(enrollment -> applicants.add(enrollment.getMember().getUsername()));


        return ResponseDto.success(new BoardDetailResponse(board, commentResponses, tags, applicants));
    }

    /**
     * 게시물 해시태그 별 조회
     */
    public ResponseDto<List<BoardResponse>> getBoardsByHashtag(Tag tag, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<Hashtag> hashtags = hashtagRepository.findAllByTag(tag);
        List<BoardResponse> boardResponses = new ArrayList<>();

        hashtags.forEach(hashtag ->
                boardResponses.add(new BoardResponse(
                        boardRepository.findById(hashtag.getBoardId()).get()
                )));

//        for (Hashtag hashtag : hashtags) {
//            Board board = boardRepository.findById(hashtag.getBoardId()).get();
//            boardResponses.add(new BoardResponse(board));
//        }
        return ResponseDto.success(boardResponses);
    }

    /**
     * 게시물 수정
     * (작성한 관리자만 수정 가능)
     */
    @Transactional
    public ResponseDto<MsgResponse> updateBoard(Member member, BoardUpdateRequest boardUpdateRequest, Long boardId) throws IOException {
        // 게시물 존재 유무
        Board board = check.existBoard(boardId);
        // 작성자인지 확인
        check.isAuthor(member, board);



        String boardImage = (boardUpdateRequest.getBoardImage() == null) ?
                board.getBoardImage() : s3Uploader.uploadFiles(boardUpdateRequest.getBoardImage(), "board");
        board.update(boardUpdateRequest, boardImage);


        // 해시태그 삭제 후 다시 등록
        hashtagRepository.deleteAllByBoardId(boardId);
        List<Tag> tags = boardUpdateRequest.getTags();
        tags.forEach(tag -> hashtagRepository.save(new Hashtag(boardId, tag)));

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

    /**
     * 게시물 검색
     */

    @Transactional
    public ResponseDto<List<BoardResponse>> searchBoards(Pageable pageable, Tag tag, String startDate, String endDate, String area) {

        List<Board> boards = boardQueryRepository.findAllBySearch(pageable, tag, startDate, endDate, area);
        check.existBoardList(boards);

        return getBoardResponses(boards);
    }

}


