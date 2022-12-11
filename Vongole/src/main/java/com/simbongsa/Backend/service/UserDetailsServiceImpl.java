package com.simbongsa.Backend.service;

import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.entity.UserDetailsImpl;
import com.simbongsa.Backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {      // DB로부터 회원정보를 가져와서, 있는 회원인지 아닌지 체크하는 메소드
        Optional<Member> member = memberRepository.findByUsername(username);
        return member
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }




}
