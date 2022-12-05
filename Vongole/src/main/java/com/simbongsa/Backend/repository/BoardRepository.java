package com.simbongsa.Backend.repository;

import com.simbongsa.Backend.entity.Board;
import com.simbongsa.Backend.entity.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 날짜별 봉사활동 목록 조회
    List<Board> findAllByDueDayBetween(Timestamp start, Timestamp end, Pageable pageable);

    // 내가 작성한 게시물 조회
    List<Board> findAllByMember(Member member, Pageable pageable);

    // 내가 작성한 게시물 조회 (boardId 가져와서 enrollRepository 조회 용도)
    List<Board> findAllByMember(Member member);

    List<Board> findAllByOrderByEndDateDesc(Pageable pageable);

//    List<Board> findAllByDueDayBetween(LocalDate start, LocalDate end);

    @Query(value = "SELECT * from board " +
            "WHERE DATE_FORMAT(due_day,'%m') =  :month" +
            " AND DATE_FORMAT(due_day,'%Y') =  :year", nativeQuery = true)
    List<Board> findAllByDueDay(@Param("year") String year,
                                @Param("month") String month);
}