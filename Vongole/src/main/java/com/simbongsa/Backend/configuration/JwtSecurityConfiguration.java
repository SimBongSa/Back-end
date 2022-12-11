package com.simbongsa.Backend.configuration;

import com.simbongsa.Backend.exception.jwt.JwtExceptionFilter;
import com.simbongsa.Backend.exception.jwt.JwtFilter;
import com.simbongsa.Backend.exception.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfiguration
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Override
    public void configure(HttpSecurity httpSecurity) {
        JwtFilter customJwtFilter = new JwtFilter(tokenProvider);
        httpSecurity.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);

        // JwtFilter 앞단에 JwtExceptionFilter 를 위치시키겠다는 설정
        httpSecurity.addFilterBefore(jwtExceptionFilter, JwtFilter.class);
    }
}
