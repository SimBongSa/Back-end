package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.response.MsgResponse;
import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.entity.*;
import com.simbongsa.Backend.repository.BoardRepository;
import com.simbongsa.Backend.repository.EnrollRepository;
import com.simbongsa.Backend.util.Check;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollService {

    private final BoardRepository boardRepository;
    private final EnrollRepository enrollRepository;
    private final Check check;
    public ResponseDto<MsgResponse> createEnrollment(Member member, Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(null);
        check.isExistedByMemberAndBoard(member, board);

        Enrollment enrollment = new Enrollment(member, board);
        enrollRepository.save(enrollment);




        return ResponseDto.success(new MsgResponse("신청이 완료되었습니다."));

    }


    public ResponseDto<MsgResponse> cancelEnrollment(Member member, Long id) {

        Enrollment enrollment = check.isEnrolled(member, id);

        enrollRepository.delete(enrollment);


        return ResponseDto.success(new MsgResponse("신청이 취소되었습니다."));
    }
}
