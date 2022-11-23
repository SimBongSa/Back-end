package com.simbongsa.Backend.repository;

import com.simbongsa.Backend.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    // 특정 유저가 특정 게시물에 찜했는지 확인
    Optional<Likes> findByUsernameAndBoardId(String username, Long boardId);

    // 게시글 삭제 시 좋아요 기록 삭제
    void deleteAllByBoardId(Long boardId);
}
