package com.simbongsa.Backend.controller;


import com.simbongsa.Backend.dto.request.*;
import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.entity.UserDetailsImpl;
import com.simbongsa.Backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members/signup/individual")
    public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto requestDto) {
        return memberService.createMember(requestDto);
    }

    @PostMapping("/members/signup/admin")
    public ResponseDto<?> signupAdmin(@ModelAttribute @Valid CompanyMemberRequestDto requestDto) throws IOException {
        return memberService.createCompanyMember(requestDto);
    }

    @PutMapping("/members/{memberId}")
    public ResponseDto<?> memberUpdate(@ModelAttribute MemberUpdateRequestDto memberUpdateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long memberId) throws IOException {
        return memberService.memberUpdate(memberUpdateRequestDto, userDetails.getMember(), memberId);
    }

    @PostMapping("/members/login")
    public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto, HttpServletResponse response) {
        return memberService.login(requestDto, response);
    }

    @PostMapping("/auth/members/reissue")
    public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        return memberService.reissue(request, response);
    }

    @PostMapping("/auth/members/logout")
    public ResponseDto<?> logout(HttpServletRequest request) {

        return memberService.logout(request);
    }

    @GetMapping("/members/signup/check_username/{username}")
    public ResponseDto<?> checkUsername(@PathVariable String username) {
        return memberService.checkUsername(username);
    }

    @PostMapping("/members/input_password")
    public ResponseDto<?> inputPasswordForUpdate(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody InputPasswordRequestDto requestDto) {
        return memberService.inputPasswordForUpdate(userDetails.getMember(), requestDto);
    }





}