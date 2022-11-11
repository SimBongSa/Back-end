package com.simbongsa.Backend.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.simbongsa.Backend.dto.request.TokenDto;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.entity.UserDetailsImpl;
import com.simbongsa.Backend.jwt.TokenProvider;
import com.simbongsa.Backend.service.KakaoUserService;
import com.simbongsa.Backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final MemberService memberService;
    private final KakaoUserService kakaoUserService;
    private final MessageSource messageSource;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;


//    @Autowired
//    public UserController(MemberService memberService, KakaoUserService kakaoUserService,MessageSource messageSource) {
//        this.memberService = memberService;
//        this.kakaoUserService = kakaoUserService;
//        this.messageSource = messageSource;
//    }

//    @ResponseBody
//    @GetMapping("/login-test")
//    public String testRequest(@AuthenticationPrincipal UserDetailsImpl userDetails){
//        log.info("[called GET:/login-test] called by user : {}", userDetails.getMember().getEmail());
//        return "user : " + userDetails.getUsername();
//    }

// https://kauth.kakao.com/oauth/authorize?client_id=e0fa0a29b6f980a77e6cad8b0f96639d&redirect_uri=http://localhost:8080/user/kakao/callback&response_type=code
//        카카오로 로그인하기
    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        log.info("[called GET:/user/kakao/callback] param code : {}", code);
//        kakaoUserService.kakaoLogin(code);
//        return "redirect:/";

        Member member = kakaoUserService.kakaoLogin(code);

        log.info("kakaoUserService.kakaoLogin complete! member = {} {}",member.getUsername(),member.getPassword());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(member.getUsername(), "kakaoUserPassword");
//                new UsernamePasswordAuthenticationToken(member.getUsername(), passwordEncoder.encode(member.getPassword()));
        log.info("authenticationToken ={}",authenticationToken);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        log.info("authenticationToken complete = {}",authentication);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        String message = messageSource.getMessage("spring.messages.frontRedirect", null, Locale.getDefault());
        log.info("message = {}", message);
        return "redirect:"+message+"?token="+tokenDto.getAccessToken();
    }
}