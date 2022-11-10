package com.simbongsa.Backend.util;

import com.simbongsa.Backend.repository.BoardRepository;
import com.simbongsa.Backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Check {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    /*
        멤버 확인
     */
}
