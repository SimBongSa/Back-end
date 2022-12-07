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
    @GetMapping("/{member_id}/comments")
    public ResponseDto<List<CommentResponse>> getMyComments(@PathVariable("member_id") Long id,
                                                            @RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "4") int size
    ){
        return myPageService.getMyComments(id,page,size);
    }
    //내가 작성한 댓글의 게시글 상세 조회


    //내가 신청한 모든 (승인,거절,기다림 포함)봉사활동 정보
    @GetMapping("/{member_id}/enroll")
    public ResponseDto<List<BoardResponse>> getMyEnroll(@PathVariable("member_id") Long id,
                                                        @RequestParam(name = "page", defaultValue = "0") int page,
                                                        @RequestParam(name = "size", defaultValue = "4") int size
    ){
        return myPageService.getMyEnroll(id,page,size);
    }

    //내가 신청한 봉사활동이 아직 대기중인 봉사활동

    @GetMapping("/{member_id}/enroll/wait")
    public ResponseDto<List<BoardResponse>> getMyEnrollWait(@PathVariable("member_id") Long id,
                                                            @RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "4") int size
    ){
        return myPageService.getMyEnrollWait(id,page,size);
    }

    //내가 신청한 봉사활동이 승인된 봉사활동

    @GetMapping("/{member_id}/enroll/pass")
    public ResponseDto<List<BoardResponse>> getMyEnrollPass(@PathVariable("member_id") Long id,
                                                            @RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "4") int size
    ){
        return myPageService.getMyEnrollPass(id,page,size);
    }

    //내가 신청한 봉사활동이 거절된 봉사활동
    @GetMapping("/{member_id}/enroll/fail")
    public ResponseDto<List<BoardResponse>> getMyEnrollFail(@PathVariable("member_id") Long id,
                                                            @RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "4") int size
    ){
        return myPageService.getMyEnrollFail(id,page,size);
    }

    //남의회원 정보 조회
    @GetMapping("/{member_id}")
    public ResponseDto<MyResponse> getYourProfile(@PathVariable("member_id") Long id) {
        return myPageService.getYourProfile(id);
    }

}
