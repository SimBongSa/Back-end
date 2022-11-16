package com.simbongsa.Backend.repository;

import com.simbongsa.Backend.entity.Board;
import com.simbongsa.Backend.entity.Enrollment;
import com.simbongsa.Backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import java.util.List;

@Repository
public interface EnrollRepository extends JpaRepository<Enrollment, Long> {

    //List<Enrollment> findAllByBoard(Board board);

    Boolean existsByMemberAndBoard(Member member, Board board);
}
