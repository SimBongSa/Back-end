package com.simbongsa.Backend.controller;

import com.simbongsa.Backend.dto.request.MyUpdateRequest;
import com.simbongsa.Backend.dto.response.BoardResponse;
import com.simbongsa.Backend.dto.response.CommentResponse;
import com.simbongsa.Backend.dto.response.MyResponse;
import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.entity.UserDetailsImpl;
import com.simbongsa.Backend.service.MyPageService;
import com.simbongsa.Backend.util.Check;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final Check check;
    private final MyPageService myPageService;

    //개인회원 정보 조회
    @GetMapping()
    public ResponseDto<MyResponse> getMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        check.isMember(userDetails.getMember());
        return myPageService.getMyProfile(userDetails.getMember());
    }

    //개인회원 정보 수정
    @PutMapping()
    public ResponseDto<MyResponse> updateMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @ModelAttribute MyUpdateRequest myUpdateRequest) throws IOException {
        check.isMember(userDetails.getMember());
        return myPageService.updateMyProfile(userDetails.getMember(), myUpdateRequest);
    }
    //내가 작성한 댓글들의 정보
    @GetMapping("/comments")
    public ResponseDto<List<CommentResponse>> getMyComments(@AuthenticationPrincipal UserDetailsImpl userDetails){
        check.isMember(userDetails.getMember());
        return myPageService.getMyComments(userDetails.getMember());
    }
    //내가 작성한 댓글의 게시글 상세 조회


    //내가 신청한 모든 (승인,거절,기다림 포함)봉사활동 정보
    @GetMapping("/enroll")
    public ResponseDto<List<BoardResponse>> getMyEnroll(@AuthenticationPrincipal UserDetailsImpl userDetails){
        check.isMember(userDetails.getMember());
        return myPageService.getMyEnroll(userDetails.getMember());
    }

    //내가 신청한 봉사활동이 아직 대기중인 봉사활동

    @GetMapping("/enroll/wait")
    public ResponseDto<List<BoardResponse>> getMyEnrollWait(@AuthenticationPrincipal UserDetailsImpl userDetails){
        check.isMember(userDetails.getMember());
        return myPageService.getMyEnrollWait(userDetails.getMember());
    }

    //내가 신청한 봉사활동이 승인된 봉사활동

    @GetMapping("/enroll/pass")
    public ResponseDto<List<BoardResponse>> getMyEnrollPass(@AuthenticationPrincipal UserDetailsImpl userDetails){
        check.isMember(userDetails.getMember());
        return myPageService.getMyEnrollPass(userDetails.getMember());
    }

    //내가 신청한 봉사활동이 거절된 봉사활동
    @GetMapping("/enroll/fail")
    public ResponseDto<List<BoardResponse>> getMyEnrollFail(@AuthenticationPrincipal UserDetailsImpl userDetails){
        check.isMember(userDetails.getMember());
        return myPageService.getMyEnrollFail(userDetails.getMember());
    }
}
