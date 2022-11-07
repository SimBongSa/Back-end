package com.simbongsa.Backend.repository;

import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByMember(Member member);
}
