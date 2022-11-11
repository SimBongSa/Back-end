package com.simbongsa.Backend.jwt;

import com.simbongsa.Backend.dto.request.TokenDto;
import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.entity.RefreshToken;
import com.simbongsa.Backend.entity.UserDetailsImpl;
import com.simbongsa.Backend.exception.ErrorCode;
import com.simbongsa.Backend.exception.GlobalException;
import com.simbongsa.Backend.repository.MemberRepository;
import com.simbongsa.Backend.repository.RefreshTokenRepository;
import com.simbongsa.Backend.service.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "Authorization";
    private static final String BEARER_TYPE = "bearer ";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; //30분

    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

    private final MemberRepository memberRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserDetailsServiceImpl userDetailsService;

    private final Key key;

    @Autowired
    public TokenProvider(@Value("${jwtSecret}") String secretKey, MemberRepository memberRepository, RefreshTokenRepository refreshTokenRepository, UserDetailsServiceImpl userDetailsService) {
        this.memberRepository = memberRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userDetailsService = userDetailsService;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto generateTokenDto(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date().getTime());

        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        Member member = ((UserDetailsImpl) authentication.getPrincipal()).getMember();

        RefreshToken refreshTokenObject = RefreshToken.builder()
                .id(member.getMemberId())
                .member(member)
                .value(refreshToken)
                .build();

        refreshTokenRepository.save(refreshTokenObject);

        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();

    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new IllegalStateException("권한 정보가 없는 토큰입니다.");
        }
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = userDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public Member getMemberFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return null;
        }
        return ((UserDetailsImpl) authentication.getPrincipal()).getMember();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    @Transactional(readOnly = true)
    public RefreshToken isPresentRefreshToken(Member member) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByMember(member);
        return optionalRefreshToken.orElse(null);
    }

    @Transactional
    public ResponseDto<?> deleteRefreshToken(Member member) {
        RefreshToken refreshToken = isPresentRefreshToken(member);
        if (null == refreshToken) {
            throw new GlobalException(ErrorCode.TOKEN_NOT_FOUND);
        }

        refreshTokenRepository.delete(refreshToken);
        return ResponseDto.success("success");
    }


}
