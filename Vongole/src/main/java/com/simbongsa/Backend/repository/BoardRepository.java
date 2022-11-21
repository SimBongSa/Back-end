package com.simbongsa.Backend.repository;

import com.simbongsa.Backend.entity.Board;
import com.simbongsa.Backend.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 날짜별 봉사활동 목록 조회
    List<Board> findAllByDueDay(LocalDate dueDay, Pageable pageable);

    // 내가 작성한 게시물 조회
    List<Board> findAllByMember(Member member, Pageable pageable);

}
