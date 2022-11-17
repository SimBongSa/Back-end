package com.simbongsa.Backend.controller;

import com.simbongsa.Backend.dto.response.MsgResponse;
import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.entity.UserDetailsImpl;
import com.simbongsa.Backend.service.EnrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EnrollController {

    private final EnrollService enrollService;

    @PostMapping("/boards/{board_id}/apply")
    public ResponseDto<MsgResponse> enrollApprovalAndCancel(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @PathVariable("board_id") Long boardId) {
        return enrollService.enrollApprovalAndCancel(userDetails.getMember(), boardId);
    }

}
