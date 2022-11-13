package com.simbongsa.Backend.controller;

import com.simbongsa.Backend.dto.request.BoardRequest;
import com.simbongsa.Backend.dto.response.BoardDetailResponse;
import com.simbongsa.Backend.dto.response.BoardResponse;
import com.simbongsa.Backend.dto.response.MsgResponse;
import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.entity.UserDetailsImpl;
import com.simbongsa.Backend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * @param multipartFile
     */
    @PostMapping()
    public ResponseDto<MsgResponse> createBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                @RequestPart(value = "boardRequest") BoardRequest boardRequest,
                                                @RequestPart(value = "file", required = false) MultipartFile multipartFile) {
        return boardService.createBoard(userDetails.getMember(), boardRequest, multipartFile);
    }

    /**
     * 게시물 날짜별 조회
     * @param dueDay
     * @return
     */
    @GetMapping("/{dueDay}")
    public ResponseDto<List<BoardResponse>> getBoards(@PathVariable String dueDay) {
        return boardService.getBoards(dueDay);
    }

    /**
     * 게시물 수정
     * @param userDetails
     * @param boardRequest
     * @param multipartFile
     * @param boardId
     * @return
     */
    @PutMapping("/{boardId}")
    public ResponseDto<BoardDetailResponse> updateBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @RequestPart(value = "boardRequest") BoardRequest boardRequest,
                                                        @RequestPart(value = "file", required = false) MultipartFile multipartFile,
                                                        @PathVariable Long boardId) {
        return boardService.updateBoard(userDetails.getMember(), boardRequest, multipartFile, boardId);
    }

    /**
     * 게시물 단건 조회
     * @param boardId
     * @return
     */
    @GetMapping("/{boardId}")
    public ResponseDto<BoardDetailResponse> getBoard(@PathVariable Long boardId) {
        return boardService.getBoard(boardId);
    }

    /**
     * 게시물 삭제
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
