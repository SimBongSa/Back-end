package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.response.MsgResponse;
import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.entity.*;
import com.simbongsa.Backend.repository.BoardRepository;
import com.simbongsa.Backend.repository.EnrollRepository;
import com.simbongsa.Backend.util.Check;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnrollService {

    private final BoardRepository boardRepository;
    private final EnrollRepository enrollRepository;
    private final Check check;

    @Transactional
    public ResponseDto<MsgResponse> enrollApprovalAndCancel(Member member, Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(null);
        check.isMember(member);

        // 신청
        if (!enrollRepository.existsByMemberAndBoard(member, board)) {



            Enrollment enrollment = new Enrollment(member, board);
            enrollRepository.save(enrollment);
            board.addApplicant();

            return ResponseDto.success(new MsgResponse("신청이 완료되었습니다."));

        //취소
        }else {
            Enrollment enrollment = enrollRepository.getEnrollmentByMemberAndBoard(member, board);
            enrollRepository.delete(enrollment);
            board.removeApplicant();

            return ResponseDto.success(new MsgResponse("신청이 취소되었습니다."));
        }




    }
}
