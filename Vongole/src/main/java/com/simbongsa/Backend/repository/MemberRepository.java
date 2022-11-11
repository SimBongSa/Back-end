package com.simbongsa.Backend.repository;

import com.simbongsa.Backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long id);
    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String userEmail);
    Optional<Member> findByKakaoId(Long id);

}
