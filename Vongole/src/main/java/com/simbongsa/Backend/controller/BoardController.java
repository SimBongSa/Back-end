package com.simbongsa.Backend.controller;

import com.simbongsa.Backend.dto.request.BoardRequest;
import com.simbongsa.Backend.dto.response.*;
import com.simbongsa.Backend.entity.UserDetailsImpl;
import com.simbongsa.Backend.service.BoardService;
import lombok.RequiredArgsConstructor;
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
    public ResponseDto<MsgResponse> createBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                @ModelAttribute @Valid BoardRequest boardRequest) throws IOException {
        return boardService.createBoard(userDetails.getMember(), boardRequest);
    }


    @GetMapping()
    public ResponseDto<List<BoardResponse>> getAllBoards() {
        return boardService.getAllBoards();
    }

    /**
     * 게시물 날짜별 조회
     */
    @GetMapping("/date/{dueDay}")
    public ResponseDto<List<BoardResponse>> getBoardsByDueDay(@PathVariable LocalDate dueDay) {
        return boardService.getBoardsByDueDay(dueDay);
    }

    /**
     * 게시물 단건 조회
     */
    @GetMapping("/{boardId}")
    public ResponseDto<BoardDetailResponse> getBoard(@PathVariable Long boardId) {
        return boardService.getBoard(boardId);
    }

    /**
     * 게시물 수정
     */
    @PutMapping("/{boardId}")
    public ResponseDto<BoardUpdateResponse> updateBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @ModelAttribute @Valid BoardRequest boardRequest,
                                                        @PathVariable Long boardId) throws IOException {
        return boardService.updateBoard(userDetails.getMember(), boardRequest, boardId);
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
}
