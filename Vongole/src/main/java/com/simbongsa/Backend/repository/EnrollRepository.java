package com.simbongsa.Backend.repository;

import com.simbongsa.Backend.entity.Board;
import com.simbongsa.Backend.entity.Enrollment;
import com.simbongsa.Backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EnrollRepository extends JpaRepository<Enrollment, Long> {

    // 봉사활동 지원자 목록
    List<Enrollment> findAllByBoard(Board board);

    // 봉사활동에 지원한 사람인지 확인
    Boolean existsByMemberAndBoard(Member member, Board board);

    // 멤버아이디로 봉사활동 지원자 조회
    Optional<Enrollment> findByMember(Member member);

    Optional<Enrollment> findByBoard(Board board);
}
