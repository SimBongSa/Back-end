package com.simbongsa.Backend.controller;

import com.simbongsa.Backend.dto.response.MsgResponse;
import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.entity.UserDetailsImpl;
import com.simbongsa.Backend.service.EnrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EnrollController {

    private final EnrollService enrollService;

    @PostMapping("/boards/{board_id}/ask")
    public ResponseDto<MsgResponse> createEnrollment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @PathVariable("board_id") Long boardId) {
        return enrollService.createEnrollment(userDetails.getMember(), boardId);
    }

    @DeleteMapping("/boards/{board_id}/ask")
    // Todo 봉사 활동 신청 시 리스펀스로 enrollId를 주는 게 아닌데 어떻게 pathVariable 로 받는지?
    // Todo pathVariable 로 boardId 안받음
    public ResponseDto<MsgResponse> cancelEnrollment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable("board_id") Long boardId) {
        return enrollService.cancelEnrollment(userDetails.getMember(), boardId);

    }
}
