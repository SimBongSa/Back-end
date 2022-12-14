package com.simbongsa.Backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simbongsa.Backend.dto.oauth.KakaoUserInfoDto;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.entity.UserDetailsImpl;
import com.simbongsa.Backend.repository.MemberRepository;
import com.simbongsa.Backend.shared.Authority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;
import java.util.UUID;

@Slf4j
@Service
public class KakaoUserService {
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final MessageSource messageSource;
    private final MemberRepository memberRepository;


    @Autowired
    public KakaoUserService(MemberService memberService, PasswordEncoder passwordEncoder, MessageSource messageSource, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
        this.memberRepository = memberRepository;

    }

    public Member kakaoLogin(String code) throws JsonProcessingException {
        // 1. "?????? ??????"??? "????????? ??????" ??????
        String accessToken = getAccessToken(code);

        // 2. ???????????? ????????? API ??????
//        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
        KakaoUserInfoDto kakaoUserInfo;
        kakaoUserInfo = getKakaoUserInfo(accessToken);
        if(kakaoUserInfo == null){
            return null;
        }

        // 3. ???????????? ????????????
//        Member kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);
        return registerKakaoUserIfNeeded(kakaoUserInfo);

        // 4. ?????? ????????? ??????
//        forceLogin(kakaoUser);
    }

    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header ??????
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        String message = messageSource.getMessage("spring.messages.kakaoRedirect", null, Locale.getDefault());

        // HTTP Body ??????
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "e0fa0a29b6f980a77e6cad8b0f96639d");
//        body.add("redirect_uri", "http://localhost:8080/user/kakao/callback");
        body.add("redirect_uri", message+"/user/kakao/callback");    //url?????? ????????? ??????
        body.add("code", code);

        // HTTP ?????? ?????????
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP ?????? (JSON) -> ????????? ?????? ??????
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header ??????
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP ?????? ?????????
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email;
        try {
            email = jsonNode.get("kakao_account")
                    .get("email").asText();
        }catch (NullPointerException e){
            return null;
        }

        System.out.println("????????? ????????? ??????: " + id + ", " + nickname + ", " + email);
        return new KakaoUserInfoDto(id, nickname, email);
    }

    private Member registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB ??? ????????? Kakao Id ??? ????????? ??????
        Long kakaoId = kakaoUserInfo.getId();
        Member kakaoUser = memberRepository.findByKakaoId(kakaoId)
                .orElse(null);
        if (kakaoUser == null) {
            // ????????? ????????? ???????????? ????????? ???????????? ?????? ????????? ????????? ??????
            String kakaoEmail = kakaoUserInfo.getEmail();
            Member sameEmailUser = memberRepository.findByEmail(kakaoEmail).orElse(null);
            if (sameEmailUser != null) {
                kakaoUser = sameEmailUser;
                // ?????? ??????????????? ????????? Id ??????
                kakaoUser.setKakaoId(kakaoId);
            } else {
                // ?????? ????????????
                // username: kakao nickname
                String nickname = kakaoUserInfo.getNickname();

                // password: random UUID
                String password = "kakaoUserPassword";
                String encodedPassword = passwordEncoder.encode(password);

                // email: kakao email
                String email = kakaoUserInfo.getEmail();
                // role: ?????? ?????????
                Authority role = Authority.ROLE_MEMBER;

//                kakaoUser = new Member(nickname, encodedPassword, email, role, kakaoId);
                kakaoUser = Member.builder()
                        .username(kakaoUserInfo.getEmail().split("@")[0])
                        .password(encodedPassword)
                        .kakaoId(kakaoId)
                        .authority(Authority.ROLE_MEMBER)
                        .email(email)
                        .name(kakaoUserInfo.getNickname())
                        .build();
            }

            memberRepository.save(kakaoUser);
        }
        return kakaoUser;
    }

    private void forceLogin(Member kakaoUser) {
        UserDetails userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}