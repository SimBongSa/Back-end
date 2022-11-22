package com.simbongsa.Backend.repository;

import com.simbongsa.Backend.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    List<Hashtag> findAllByBoardId(Long boardId);
}
