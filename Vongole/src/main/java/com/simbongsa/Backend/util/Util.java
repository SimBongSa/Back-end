package com.simbongsa.Backend.util;

import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Util {

    private final MemberRepository memberRepository;

    public Member getMember(Long memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));
        return member;
    }


}
