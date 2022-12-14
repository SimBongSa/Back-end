package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.request.CompanyUpdateRequest;
import com.simbongsa.Backend.dto.response.*;
import com.simbongsa.Backend.entity.Board;
import com.simbongsa.Backend.entity.Enrollment;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.repository.BoardRepository;
import com.simbongsa.Backend.repository.EnrollRepository;
import com.simbongsa.Backend.repository.HashtagRepository;
import com.simbongsa.Backend.repository.MemberRepository;
import com.simbongsa.Backend.util.Check;
import com.simbongsa.Backend.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyPageService {

    private final EnrollRepository enrollRepository;
    private final BoardRepository boardRepository;
    private final HashtagRepository hashtagRepository;

    private final PasswordEncoder passwordEncoder;
    private final Check check;
    private final S3Uploader s3Uploader;
    private final MemberRepository memberRepository;

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
    public ResponseDto<MsgResponse> updateMyProfile(Member member, CompanyUpdateRequest companyUpdateRequest) throws IOException {
        check.isAdmin(member);

        // 시큐리티에서 제공하는 member 객체에 db 로부터 불러온 member 객체 덮어 쓰기
        Member findMember = check.findMember(member.getMemberId());
        check.isPassword(companyUpdateRequest.getPassword(), companyUpdateRequest.getPasswordConfirm());

        String encodedPassword = passwordEncoder.encode(companyUpdateRequest.getPassword());

        String profileImage = (companyUpdateRequest.getProfileImage() == null) ?
                findMember.getProfileImage() : s3Uploader.uploadFiles(companyUpdateRequest.getProfileImage(), "company");

        findMember.update(companyUpdateRequest, profileImage, encodedPassword);


        return ResponseDto.success(new MsgResponse("수정 완료!"));
    }

    /**
     * 내 게시물 목록
     */
    public ResponseDto<List<BoardResponse>> getMyBoards(Member member, int page, int size) {
        check.isAdmin(member);
        Pageable pageable = PageRequest.of(page, size);
        List<BoardResponse> boardResponses = boardRepository.findAllByMember(member,pageable).stream()
                .map(BoardResponse::new)
                .collect(Collectors.toList());

        return ResponseDto.success(boardResponses);
    }

    /**
     * 내 게시물 지원자 전체 조회
     */
    public ResponseDto<List<EnrollDetailResponse>> getVolunteers(Member member, int page, int size) {
        // TODO : 페이징 적용
        check.isAdmin(member);
        Pageable pageable = PageRequest.of(page, size);
        List<Board> myBoards = boardRepository.findAllByMember(member);
        List<EnrollDetailResponse> enrollDetailResponses = new ArrayList<>();
        for (Board myBoard : myBoards) {

            List<String> tags = hashtagRepository.findAllByBoardId(myBoard.getId()).stream()
                    .map(hashtag -> hashtag.getTag().getMsg())
                    .collect(Collectors.toList());

            List<Enrollment> enrollments = enrollRepository.findAllByBoard(myBoard);
            enrollments.forEach(enrollment -> enrollDetailResponses.add(new EnrollDetailResponse(enrollment, tags)));
        }
        return ResponseDto.success(enrollDetailResponses);
    }

    /**
     * 게시물 별 봉사 활동 지원자 목록
     */
    public ResponseDto<List<EnrollResponse>> getVolunteersByBoardId(Member member, Long boardId, int page, int size) {
        check.isAdmin(member);
        Pageable pageable = PageRequest.of(page, size);

        // board 존재하는지 체크
        Board board = check.existBoard(boardId);

        List<EnrollResponse> enrollResponses = enrollRepository.findAllByBoard(board, pageable).stream()
                .map(EnrollResponse::new)
                .collect(Collectors.toList());

        return ResponseDto.success(enrollResponses);
    }

    /**
     * 봉사 활동 지원자 승인
     */
    @Transactional
    public ResponseDto<EnrollResponse> approveMember(Member member, Long enrollId) {
        check.isAdmin(member);
        Enrollment applicant = check.isEnrolled(enrollId);

        // 내 게시물의 신청자가 아니면 접근 막기
        check.myApplicant(applicant, member);

        applicant.approve();

        return ResponseDto.success(new EnrollResponse(applicant));
    }

    /**
     * 봉사 활동 지원자 거절
     */
    @Transactional
    public ResponseDto<EnrollResponse> disapproveMember(Member member, Long enrollId) {
        check.isAdmin(member);
        Enrollment applicant = check.isEnrolled(enrollId);

        check.myApplicant(applicant, member);

        applicant.disapprove();

        return ResponseDto.success(new EnrollResponse(applicant));
    }


    public ResponseDto<CompanyResponse> getYourProfile(Long id) {
        Optional<Member> member = memberRepository.findByMemberId(id);
        check.isAdmin(member.orElseThrow());
        return ResponseDto.success(new CompanyResponse(member.orElseThrow()));
    }

    public ResponseDto<List<BoardResponse>> getYourBoards(Long id, int page, int size) {
        Optional<Member> member = memberRepository.findByMemberId(id);
        check.isAdmin(member.orElseThrow());
        Pageable pageable = PageRequest.of(page, size);
        List<Board> myBoards = boardRepository.findAllByMember(member.orElseThrow(), pageable);
        List<BoardResponse> boardResponses = new ArrayList<>();

        for (Board myBoard : myBoards) {
            boardResponses.add(new BoardResponse(myBoard));
        }

        return ResponseDto.success(boardResponses);
    }
}
