package com.simbongsa.Backend.repository;

import com.simbongsa.Backend.entity.Board;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    // 봉사활동에 지원한 지원자 목록 조회
    List<Volunteer> findAllByBoard(Board board);

    // 아이디로 봉사 활동 지원자 조회
    // Todo 이미 지원자가 있는게 확실하니까 Optional 로 안받아도 되겠지?
    Volunteer findByMember(Member member);
}
