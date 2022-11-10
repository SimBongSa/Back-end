package com.simbongsa.Backend.controller;

import com.simbongsa.Backend.dto.request.BoardRequest;
import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.entity.UserDetailsImpl;
import com.simbongsa.Backend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    /**
     *  게시물 생성
     * @param userDetails
     * @param boardRequest
     * @param multipartFile
     */
    @PostMapping()
    public ResponseDto<BoardRequest> createBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @RequestPart(value = "boardRequest") BoardRequest boardRequest,
                                                 @RequestPart(value = "file", required = false) MultipartFile multipartFile) {
        return boardService.createBoard(userDetails.getMember(), boardRequest, multipartFile);
    }
}
