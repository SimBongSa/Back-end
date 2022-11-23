package com.simbongsa.Backend.repository;

import com.simbongsa.Backend.entity.Approval;
import com.simbongsa.Backend.entity.Board;
import com.simbongsa.Backend.entity.Enrollment;
import com.simbongsa.Backend.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EnrollRepository extends JpaRepository<Enrollment, Long> {

    // 봉사활동 지원자 목록
    List<Enrollment> findAllByBoard(Board board, Pageable pageable);

    // 봉사활동에 지원한 사람인지 확인
    Boolean existsByMemberAndBoard(Member member, Board board);

    Enrollment getEnrollmentByMemberAndBoard(Member member, Board board);


    Page<Enrollment> findAllByMember(Member member,Pageable pageable);

    List<Enrollment> findAllByMemberAndApproval(Approval approval, Member member);

    List<Enrollment> findAllByBoard(Board myBoard);
}
