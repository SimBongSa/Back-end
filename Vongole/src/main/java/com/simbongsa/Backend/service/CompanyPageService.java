package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.request.CompanyUpdateRequest;
import com.simbongsa.Backend.dto.response.*;
import com.simbongsa.Backend.entity.Board;
import com.simbongsa.Backend.entity.Enrollment;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.repository.BoardRepository;
import com.simbongsa.Backend.repository.EnrollRepository;
import com.simbongsa.Backend.util.Check;
import com.simbongsa.Backend.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyPageService {

    private final EnrollRepository enrollRepository;
    private final BoardRepository boardRepository;

    private final Check check;
    private final S3Uploader s3Uploader;

    /**
     * 내 프로필 조회
     */
    public ResponseDto<CompanyResponse> getMyProfile(Member member) {
        check.isAdmin(member);
        return ResponseDto.success(new CompanyResponse(member));
    }


    /**
     * 내 프로필 수정
     * 관리자인지 확인
     * 비밀번호 두개 일치하는지 확인
     */
    @Transactional
    public ResponseDto<CompanyResponse> updateMyProfile(Member member, CompanyUpdateRequest companyUpdateRequest) throws IOException {
        check.isAdmin(member);
        check.isPassword(companyUpdateRequest.getPassword(), companyUpdateRequest.getPasswordConfirm());
        String profileImage = s3Uploader.uploadFiles(companyUpdateRequest.getProfileImage(), "company");
        member.update(companyUpdateRequest, profileImage);

        return ResponseDto.success(new CompanyResponse(member));
    }

    /**
     * 내 게시물 목록
     */
    public ResponseDto<List<BoardResponse>> getMyBoards(Member member, int page, int size) {
        check.isAdmin(member);
        Pageable pageable = PageRequest.of(page, size);
        List<Board> myBoards = boardRepository.findAllByMember(member, pageable);
        List<BoardResponse> boardResponses = new ArrayList<>();

        for (Board myBoard : myBoards) {
            boardResponses.add(new BoardResponse(myBoard));
        }

        return ResponseDto.success(boardResponses);
    }

    /**
     * 봉사 활동 지원자 목록
     */
    public ResponseDto<List<EnrollResponse>> getVolunteers(Member member, Long boardId, int page, int size) {
        check.isAdmin(member);
        Pageable pageable = PageRequest.of(page, size);

        // board 존재하는지 체크
        Board board = check.existBoard(boardId);

        List<Enrollment> enrollments = enrollRepository.findAllByBoard(board, pageable);
        List<EnrollResponse> enrollResponses = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            enrollResponses.add(new EnrollResponse(enrollment));
        }

        return ResponseDto.success(enrollResponses);
    }

    /**
     * 봉사 활동 지원자 승인
     */
    @Transactional
    public ResponseDto<MsgResponse> approveMember(Member member, Long enrollId) {
        check.isAdmin(member);
        Enrollment applicant = check.isEnrolled(enrollId);

        applicant.approve();

        return ResponseDto.success(new MsgResponse(applicant.getMember().getUsername() + " 님, 승인 완료"));
    }

    /**
     * 봉사 활동 지원자 거절
     */
    @Transactional
    public ResponseDto<MsgResponse> disapproveMember(Member member, Long enrollId) {
        check.isAdmin(member);
        Enrollment applicant = check.isEnrolled(enrollId);

        applicant.disapprove();

        return ResponseDto.success(new MsgResponse(applicant.getMember().getUsername() + " 님, 승인 거절"));
    }
}
