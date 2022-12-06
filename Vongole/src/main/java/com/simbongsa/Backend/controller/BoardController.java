package com.simbongsa.Backend.controller;

import com.simbongsa.Backend.dto.request.BoardRequest;
import com.simbongsa.Backend.dto.request.BoardUpdateRequest;
import com.simbongsa.Backend.dto.response.*;
import com.simbongsa.Backend.entity.Tag;
import com.simbongsa.Backend.entity.UserDetailsImpl;
import com.simbongsa.Backend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시물 생성
     */
    @PostMapping()
    public ResponseDto<BoardCreateResponse> createBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @ModelAttribute @Valid BoardRequest boardRequest) throws IOException {

        return boardService.createBoard(userDetails.getMember(), boardRequest);
    }


    /**
     *  게시물 전체 조회
     */
    @GetMapping()
    public ResponseDto<List<BoardResponse>> getAllBoards(@RequestParam(name = "page", defaultValue = "1") int page,
                                                         @RequestParam(name = "size", defaultValue = "4") int size) {

        return boardService.getAllBoards(page - 1, size);
    }

    /**
     * 게시물 월별 조회
     */
    @GetMapping("/month")
    public ResponseDto<List<BoardDueDayResponse>> getBoardsByMonth(@RequestParam(name = "year") String year,
                                                                   @RequestParam(name = "month") String month) {
        return boardService.getBoardsByMonth(year,month);
    }

    /**
     * 게시물 날짜별 조회
     */
    @GetMapping("/date/{dueDay}")
    public ResponseDto<List<BoardResponse>> getBoardsByDueDay(@PathVariable LocalDate dueDay,
                                                              @RequestParam(name = "page", defaultValue = "1") int page,
                                                              @RequestParam(name = "size", defaultValue = "4") int size) {

        return boardService.getBoardsByDueDay(dueDay, page - 1, size);
    }

    /**
     * 게시물 단건 조회
     */
    @GetMapping("/{boardId}")
    public ResponseDto<BoardDetailResponse> getBoard(@PathVariable Long boardId,
                                                     @RequestParam(name = "page", defaultValue = "1") int page,
                                                     @RequestParam(name = "size", defaultValue = "5") int size) {

        return boardService.getBoard(boardId, page - 1, size);
    }

    /**
     *  게시물 해시태그 별 조회
     */
    @GetMapping("/tag")
    public ResponseDto<List<BoardResponse>> getBoardsByHashtag(@RequestParam(name = "tag") Tag tag,
                                                         @RequestParam(name = "page", defaultValue = "1") int page,
                                                         @RequestParam(name = "size", defaultValue = "4") int size) {
        return boardService.getBoardsByHashtag(tag, page, size);
    }

    /**
     * 게시물 수정
     */
    @PutMapping("/{boardId}")
    public ResponseDto<MsgResponse> updateBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                @ModelAttribute @Valid BoardUpdateRequest boardUpdateRequest,
                                                @PathVariable Long boardId) throws IOException {
        return boardService.updateBoard(userDetails.getMember(), boardUpdateRequest, boardId);
    }

    /**
     * 게시물 삭제
     */
    @DeleteMapping("/{boardId}")
    public ResponseDto<MsgResponse> deleteBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                @PathVariable Long boardId) {
        return boardService.deleteBoard(userDetails.getMember(), boardId);
    }

    /**
     * 게시물 찜 or 찜 취소
     */
    @PostMapping("/{boardId}/like")
    public ResponseDto<MsgResponse> likeBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                              @PathVariable Long boardId) {
        return boardService.likeBoard(userDetails.getUsername(), boardId);
    }

    /**
     * 게시물 검색
     */
    @GetMapping("/search")
    public ResponseDto<List<BoardResponse>> searchBoards(Pageable pageable,
                                                         @RequestParam(name = "tag") Tag tag,
                                                         @RequestParam(name = "startDate") String startDate,
                                                         @RequestParam(name = "endDate") String endDate,
                                                         @RequestParam(name = "area") String area) {

        return boardService.searchBoards(pageable, tag, startDate, endDate, area);

    }
}
