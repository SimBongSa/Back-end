package com.simbongsa.Backend.repository;

import com.simbongsa.Backend.entity.Board;
import com.simbongsa.Backend.entity.Comment;
import com.simbongsa.Backend.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 게시물 상세조회 시 전체 댓글 조회
    List<Comment> findAllByBoard(Board board, Pageable pageable);

    Page<Comment> findAllByMember(Member member, Pageable pageable);
}
