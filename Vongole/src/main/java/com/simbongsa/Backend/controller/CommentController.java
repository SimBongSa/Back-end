package com.simbongsa.Backend.controller;

import com.simbongsa.Backend.dto.request.CommentRequest;
import com.simbongsa.Backend.dto.response.MsgResponse;
import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.entity.UserDetailsImpl;
import com.simbongsa.Backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    //댓글 생성
    @PostMapping("/{board_id}")
    public ResponseDto<MsgResponse> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable("board_id") Long id,
                                                  @RequestBody CommentRequest commentRequest) {
        return commentService.createComment(userDetails.getMember(), id, commentRequest);
    }

    //댓글 수정
    @PutMapping("/{comment_id}")
    public ResponseDto<MsgResponse> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable("comment_id") Long id,
                                                  @RequestBody CommentRequest commentRequest) {
        return commentService.updateComment(userDetails.getMember(), id, commentRequest);
    }

    //댓글 삭제
    @DeleteMapping("/}comment_id}")
    public ResponseDto<MsgResponse> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable("comment_id") Long id) {
        return commentService.deleteComment(userDetails.getMember(), id);
    }
}
