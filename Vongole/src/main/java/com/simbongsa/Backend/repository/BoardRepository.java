package com.simbongsa.Backend.repository;

import com.simbongsa.Backend.entity.Board;
import com.simbongsa.Backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 날짜별 봉사활동 목록 조회
    List<Board> findAllByDueDay(String dueDay);

    // 게시물 작성한 유저인지 확인
    Optional<Board> findByMember(Member member);


    // 내가 작성한 게시물 조회
    List<Board> findAllByMember(Member member);
}
