package com.simbongsa.Backend.controller;

import com.simbongsa.Backend.dto.request.BoardRequest;
import com.simbongsa.Backend.dto.response.*;
import com.simbongsa.Backend.entity.UserDetailsImpl;
import com.simbongsa.Backend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시물 생성
     *
     * @param userDetails
     * @param boardRequest
     */
    @PostMapping()
    public ResponseDto<MsgResponse> createBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                @ModelAttribute BoardRequest boardRequest) throws IOException {
        return boardService.createBoard(userDetails.getMember(),boardRequest);
}

    /**
     * 게시물 날짜별 조회
     *
     * @param dueDay
     * @return
     */
    @GetMapping("/date/{dueDay}")
    public ResponseDto<List<BoardResponse>> getBoards(@PathVariable String dueDay) {
        return boardService.getBoards(dueDay);
    }

    /**
     * 게시물 단건 조회
     *
     * @param boardId
     * @return
     */
    @GetMapping("/{boardId}")
    public ResponseDto<BoardDetailResponse> getBoard(@PathVariable Long boardId) {
        return boardService.getBoard(boardId);
    }

    /**
     * 게시물 수정
     *
     * @param userDetails
     * @param boardRequest
     * @param boardId
     * @return
     */
    @PutMapping("/{boardId}")
    public ResponseDto<BoardUpdateResponse> updateBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @ModelAttribute BoardRequest boardRequest,
                                                        @PathVariable Long boardId) throws IOException {
        return boardService.updateBoard(userDetails.getMember(), boardRequest, boardId);
    }

    /**
     * 게시물 삭제
     *
     * @param userDetails
     * @param boardId
     * @return
     */
    @DeleteMapping("/{boardId}")
    public ResponseDto<MsgResponse> deleteBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                @PathVariable Long boardId) {
        return boardService.deleteBoard(userDetails.getMember(), boardId);
    }

    /**
     * 게시물 찜 or 찜 취소
     *
     * @param userDetails
     * @param boardId
     * @return
     */
    @PostMapping("/{boardId}/like")
    public ResponseDto<MsgResponse> likeBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                              @PathVariable Long boardId) {
        return boardService.likeBoard(userDetails.getUsername(), boardId);
    }
}
