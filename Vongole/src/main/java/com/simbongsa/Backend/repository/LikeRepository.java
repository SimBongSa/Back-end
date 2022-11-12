package com.simbongsa.Backend.repository;

import com.simbongsa.Backend.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // 특정 유저가 특정 게시물에 찜했는지 확인
    Optional<Like> findByUsernameAndBoardId(String username, Long boardId);
}
