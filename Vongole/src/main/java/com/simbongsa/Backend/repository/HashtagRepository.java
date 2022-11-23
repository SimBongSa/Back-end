package com.simbongsa.Backend.repository;

import com.simbongsa.Backend.entity.Hashtag;
import com.simbongsa.Backend.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    // 게시글에 달린 해시태그 조회
    List<Hashtag> findAllByBoardId(Long boardId);

    // 게시글 삭제 시, 달린 해시태그 모두 삭제
    void deleteAllByBoardId(Long boardId);

    // 태그로 조회
    List<Hashtag> findAllByTag(Tag tag);

}
