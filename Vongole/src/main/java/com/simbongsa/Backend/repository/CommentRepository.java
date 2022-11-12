package com.simbongsa.Backend.repository;

import com.simbongsa.Backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
