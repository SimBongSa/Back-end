package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.response.*;
import com.simbongsa.Backend.entity.Board;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.entity.Volunteer;
import com.simbongsa.Backend.repository.BoardRepository;
import com.simbongsa.Backend.repository.MemberRepository;
import com.simbongsa.Backend.repository.VolunteerRepository;
import com.simbongsa.Backend.util.Check;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyPageService {

    private final VolunteerRepository volunteerRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private Check check;


    /**
     * 내 프로필 조회
     *
     * @param member
     * @return
     */
    public ResponseDto<CompanyResponse> getMyProfile(Member member) {
        return ResponseDto.success(new CompanyResponse(member));
    }


    /**
     * 내 프로필 수정
     *
     * @param member
     * @return
     */
    public ResponseDto<CompanyResponse> updateMyProfile(Member member) {
        // Todo 이미지 업로드되면 시작
        return null;
    }

    /**
     * 내 게시물 목록
     *
     * @param member
     * @return
     */
    public ResponseDto<List<BoardResponse>> getMyBoards(Member member) {
        // Todo 관리자인지 확인??

        List<Board> myBoards = boardRepository.findAllByMember(member);
        List<BoardResponse> boardResponses = new ArrayList<>();

        for (Board myBoard : myBoards) {
            boardResponses.add(new BoardResponse(myBoard));
        }

        return ResponseDto.success(boardResponses);
    }

    /**
     * 봉사 활동 지원자 목록
     *
     * @param member
     * @param boardId
     * @return
     */
    public ResponseDto<List<VolunteerResponse>> getVolunteers(Member member, Long boardId) {
        // Todo 관리자인지 확인??

        // Todo 어차피 확정된 아이디값인데 optional 로 받아서 존재하는 board 인지 확인할 필요가 있나?
        Board board = boardRepository.findById(boardId).get();

        List<Volunteer> volunteers = volunteerRepository.findAllByBoard(board);
        List<VolunteerResponse> volunteerResponses = new ArrayList<>();

        for (Volunteer volunteer : volunteers) {
            volunteerResponses.add(new VolunteerResponse(volunteer));
        }

        return ResponseDto.success(volunteerResponses);
    }

    /**
     * 봉사 활동 지원자 승인
     *
     * @param member
     * @param memberId
     * @return
     */
    @Transactional
    public ResponseDto<MsgResponse> approveMember(Member member, Long memberId) {

        // Optional 을 어떻게 해결해야 하지
        Member findMember = memberRepository.findById(memberId).get();

        Volunteer volunteer = volunteerRepository.findByMember(findMember);

        volunteer.pass();

        return ResponseDto.success(new MsgResponse(findMember.getUsername() + " 님, 승인 완료"));
    }

    /**
     * 봉사 활동 지원자 거절
     *
     * @param member
     * @param memberId
     * @return
     */
    @Transactional
    public ResponseDto<MsgResponse> disapproveMember(Member member, Long memberId) {
        // Optional 을 어떻게 해결해야 하지
        Member findMember = memberRepository.findById(memberId).get();

        Volunteer volunteer = volunteerRepository.findByMember(findMember);

        volunteer.fail();

        return ResponseDto.success(new MsgResponse(findMember.getUsername() + " 님, 승인 거절"));
    }
}
