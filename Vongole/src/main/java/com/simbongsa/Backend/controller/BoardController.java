package com.simbongsa.Backend.controller;

import com.simbongsa.Backend.dto.request.BoardRequest;
import com.simbongsa.Backend.dto.response.*;
import com.simbongsa.Backend.entity.Board;
import com.simbongsa.Backend.entity.UserDetailsImpl;
import com.simbongsa.Backend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
//        HttpHeaders headers = new HttpHeaders();
//        return new ResponseEntity<>(boardService.createBoard(userDetails.getMember(), boardRequest),headers , HttpStatus.OK);

        return boardService.createBoard(userDetails.getMember(), boardRequest);
    }


    @GetMapping()
    public ResponseDto<List<BoardResponse>> getAllBoards(@RequestParam(name = "page", defaultValue = "1") int page,
                                                         @RequestParam(name = "size", defaultValue = "4") int size) {
        page = page - 1;
        return boardService.getAllBoards(page, size);
    }

    /**
     * 게시물 날짜별 조회
     */
    @GetMapping("/date/{dueDay}")
    public ResponseDto<List<BoardResponse>> getBoardsByDueDay(@PathVariable LocalDate dueDay,
                                                              @RequestParam(name = "page", defaultValue = "1") int page,
                                                              @RequestParam(name = "size", defaultValue = "4") int size) {
        page = page - 1;

        return boardService.getBoardsByDueDay(dueDay, page, size);
    }

    /**
     * 게시물 단건 조회
     */
    @GetMapping("/{boardId}")
    public ResponseDto<BoardDetailResponse> getBoard(@PathVariable Long boardId,
                                                     @RequestParam(name = "page", defaultValue = "1") int page,
                                                     @RequestParam(name = "size", defaultValue = "5") int size) {
        page = page - 1;

        return boardService.getBoard(boardId, page, size);
    }

    /**
     * 게시물 수정
     */
    @PutMapping("/{boardId}")
    public ResponseDto<MsgResponse> updateBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
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
