package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.request.*;
import com.simbongsa.Backend.dto.response.MemberResponseDto;
import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.entity.RefreshToken;
import com.simbongsa.Backend.jwt.TokenProvider;
import com.simbongsa.Backend.repository.MemberRepository;
import com.simbongsa.Backend.repository.RefreshTokenRepository;
import com.simbongsa.Backend.util.Check;
import com.simbongsa.Backend.util.S3Uploader;
import com.simbongsa.Backend.shared.Authority;
import com.simbongsa.Backend.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {




    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;


    private final EntityManager entityManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final Util util;
    private final S3Uploader s3Uploader;
    private final Check check;



    @Transactional
    public ResponseDto<?> createMember(MemberRequestDto requestDto) {

        check.isDuplicated(requestDto.getUsername());
        check.isPassword(requestDto.getPassword(), requestDto.getPasswordConfirm());




            // 일반회원
            Member member = Member.builder()
                    .username(requestDto.getUsername())
                    .nickname(requestDto.getNickname())
                    .password(passwordEncoder.encode(requestDto.getPassword()))
                    .email(requestDto.getEmail())
                    .phoneNumber(requestDto.getPhoneNumber())
                    .name(requestDto.getName())
                    .gender(requestDto.getGender())
                    .birthdate(requestDto.getBirthdate())
                    .authority(requestDto.getAuthority())
                    .build();



            memberRepository.save(member);

            return ResponseDto.success(
                    MemberResponseDto.builder()
                            .id(member.getMemberId())
                            .username(member.getUsername())
                            .authority(member.getAuthority())
                            .createdAt(member.getCreatedAt())
                            .modifiedAt(member.getModifiedAt())
                            .build()
            );



    }

    // 기업회원
    @Transactional
    public ResponseDto<?> createCompanyMember(CompanyMemberRequestDto requestDto) {

        check.isDuplicated(requestDto.getUsername());
        check.isPassword(requestDto.getPassword(), requestDto.getPasswordConfirm());



//
//        if (requestDto.getAuthority() != Authority.ROLE_ADMIN) {
//
//
//
//        }

            Member member = Member.builder()
                    .username(requestDto.getUsername())
                    .nickname(requestDto.getNickname())
                    .password(passwordEncoder.encode(requestDto.getPassword()))
                    .email(requestDto.getEmail())
                    .phoneNumber(requestDto.getPhoneNumber())
                    .name(requestDto.getName())
                    .licenseNumber(requestDto.getLicenseNumber())
                    .licenseImage(requestDto.getLicenseImage())
                    .authority(requestDto.getAuthority())
                    .build();





            memberRepository.save(member);

            return ResponseDto.success(
                    MemberResponseDto.builder()
                            .id(member.getMemberId())
                            .username(member.getUsername())
                            .authority(member.getAuthority())
                            .createdAt(member.getCreatedAt())
                            .modifiedAt(member.getModifiedAt())
                            .build()
            );



    }








    @Transactional
    public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = check.isPresentMember(requestDto.getUsername());
        check.isNotMember(member);


        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        tokenToHeaders(tokenDto, response);

        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getMemberId())
                        .username(member.getUsername())
                        .authority(member.getAuthority())
                        .build()
        );
    }

    public ResponseDto<?> memberUpdate(MemberUpdateRequestDto memberUpdateRequestDto, Member member, Long memberId) throws IOException {

        Member preMember = util.getMember(member.getMemberId());
        check.isSameMember(preMember, member, memberId);

        Member newMember = Member.builder()
                .memberId(preMember.getMemberId())
                .username(preMember.getUsername())
                .nickname(memberUpdateRequestDto.getNickname())
                .password(preMember.getPassword())
                .introduction(memberUpdateRequestDto.getIntroduction())
                .authority(preMember.getAuthority())
                .profileImage(
                        (memberUpdateRequestDto.getProfileImage().getOriginalFilename().equals(""))?
                                null:s3Uploader.uploadFiles(memberUpdateRequestDto.getProfileImage(), "member", member, "member"))
                .build();

        memberRepository.save(newMember);
        return ResponseDto.success("회원정보가 정상적으로 수정되었습니다.");
    }




    @Transactional
    public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        check.isValidToken("Refresh-Token");
        Member member = tokenProvider.getMemberFromAuthentication();
        check.isNotMember(member);


        Authentication authentication = tokenProvider.getAuthentication(request.getHeader("Access-Token"));
        RefreshToken refreshToken = tokenProvider.isPresentRefreshToken(member);

        check.isSameToken(refreshToken, "Refresh-Token");

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        refreshToken.updateValue(tokenDto.getRefreshToken());
        tokenToHeaders(tokenDto, response);
        return ResponseDto.success("success");
    }

    public ResponseDto<?> logout(HttpServletRequest request) {

        check.isValidToken("Refresh-Token");
        Member member = tokenProvider.getMemberFromAuthentication();
        check.isNotMember(member);

        return tokenProvider.deleteRefreshToken(member);
    }



    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Access-Token", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", "Bearer " + tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }


}
