package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.request.*;
import com.simbongsa.Backend.dto.response.MemberResponseDto;
import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.entity.RefreshToken;
import com.simbongsa.Backend.jwt.TokenProvider;
import com.simbongsa.Backend.repository.MemberRepository;
import com.simbongsa.Backend.util.Check;
import com.simbongsa.Backend.util.S3Uploader;
import com.simbongsa.Backend.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MemberService {




    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    private final Util util;
    private final S3Uploader s3Uploader;
    private final Check check;

    @Value("${defaultImage}")
    private String defaultImage;


    @Transactional
    public ResponseDto<?> createMember(MemberRequestDto requestDto) {

        check.isPassword(requestDto.getPassword(), requestDto.getPasswordConfirm());



            // 일반회원
            Member member = Member.builder()
                    .username(requestDto.getUsername())
                    .password(passwordEncoder.encode(requestDto.getPassword()))
                    .profileImage(defaultImage)
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
    public ResponseDto<?> createCompanyMember(CompanyMemberRequestDto requestDto) throws IOException {

        check.isPassword(requestDto.getPassword(), requestDto.getPasswordConfirm());


            Member member = Member.builder()
                    .username(requestDto.getUsername())
                    .password(passwordEncoder.encode(requestDto.getPassword()))
                    .email(requestDto.getEmail())
                    .phoneNumber(requestDto.getPhoneNumber())
                    .name(requestDto.getName())
                    .licenseNumber(requestDto.getLicenseNumber())
                    .profileImage(defaultImage)
                    .licenseImage((requestDto.getLicenseImage().getOriginalFilename().equals(""))?
                            null: s3Uploader.uploadFiles(requestDto.getLicenseImage(), "licenseImage"))
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
                .password(preMember.getPassword())
                .introduction(memberUpdateRequestDto.getIntroduction())
                .authority(preMember.getAuthority())
                .profileImage((memberUpdateRequestDto.getProfileImage().getOriginalFilename().equals(""))?
                        null:s3Uploader.uploadFiles(memberUpdateRequestDto.getProfileImage(), "member"))
                .build();

        memberRepository.save(newMember);
        return ResponseDto.success("회원정보가 정상적으로 수정되었습니다.");
    }

    @Transactional
    public ResponseDto<?> inputPasswordForUpdate(Member member, InputPasswordRequestDto requestDto) {
        check.isNotMember(member);
        Member dbMember = memberRepository.findByMemberId(member.getMemberId()).orElseThrow();

        String dbPassword = dbMember.getPassword();
        String inputPassword = requestDto.getPassword();

        check.isMatched(inputPassword, dbPassword);

        return ResponseDto.success("success");

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


    public ResponseDto<?> checkUsername(String username) {

        check.isDuplicatedUsername(username);
        return ResponseDto.success("사용 가능한 아이디 입니다.");

    }

}
