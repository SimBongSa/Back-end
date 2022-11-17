package com.simbongsa.Backend.util;

import com.simbongsa.Backend.entity.*;
import com.simbongsa.Backend.exception.ErrorCode;
import com.simbongsa.Backend.exception.GlobalException;
import com.simbongsa.Backend.jwt.TokenProvider;
import com.simbongsa.Backend.repository.*;
import com.simbongsa.Backend.shared.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Check {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;

    private final EnrollRepository enrollRepository;


    private final TokenProvider tokenProvider;

    /*
        멤버 확인 (username 중복 유무)
     */
    public Member isPresentMember(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        return optionalMember.orElse(null);
    }

    /*
        멤버 확인 (member가 null일 때)
     */

    public Member isNotMember(Member member) {
        if (null == member) {
            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
        }

        return member;
    }

    /*
        멤버 확인 (동일 유무)
     */

    public Member isSameMember(Member preMember, Member member, Long memberId) {

        if (!preMember.getMemberId().equals(memberId)) {
            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
        }

        return member;
    }

    /*
        이미 신청된 유저인지 확인
     */
    public void isExistedByMemberAndBoard(Member member, Board board) {
        if (enrollRepository.existsByMemberAndBoard(member, board)) {
            throw new GlobalException(ErrorCode.ALREADY_APPLIED);
        }

    }

       /*
        이미 승인된 유저인지 확인
     */


    /*
        관리자인지 확인
     */
    public void isAdmin(Member member) {
        if (member.getAuthority() != Authority.ROLE_ADMIN) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED_USER);
        }
    }


    /*
        중복 확인
     */

    public void isDuplicated(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        if (null != isPresentMember(username)) {
            throw new GlobalException(ErrorCode.DUPLICATE_NICKNAME);
        }

    }

    /*
        비밀번호 일치 여부 확인
     */

    public void isPassword(String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm)) {
            throw new GlobalException(ErrorCode.PASSWORD_NOT_MATCHED);
        }
    }

    /*
        토큰 유효 여부
     */

    public void isValidToken(String getHeader) {
        if (!tokenProvider.validateToken(getHeader)) {
            throw new GlobalException(ErrorCode.INVALID_TOKEN);
        }
    }

    public void isSameToken(RefreshToken refreshToken, String getHeader) {
        if (!refreshToken.getValue().equals(getHeader)) {
            throw new GlobalException(ErrorCode.INVALID_TOKEN);
        }
    }


    /*
        게시글 존재 유무 확인
     */
    public Board existBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);
        if (board == null) {
            throw new GlobalException(ErrorCode.BOARD_NOT_FOUND);
        }
        return board;
    }


    /*
        게시글 작성한 유저인지 확인
     */
    public void isAuthor(Member member, Board board) {
        // 관리자 테이블 나눌건지 회의 후 수정해야 함
        if (!board.getMember().getUsername().equals(member.getUsername())) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED_AUTHOR);
        }
    }

    /*
        댓글 존재 유무 확인
     */
    public Comment isComment(Member member,Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) {
            throw new GlobalException(ErrorCode.COMMENT_NOT_FOUND);
        }
        if (!comment.getMember().getUsername().equals(member.getUsername())){
            throw new GlobalException(ErrorCode.UNAUTHORIZED_AUTHOR);
        }
        return comment;
    }

    public Enrollment isEnrolled(Member member, Long id) {
        Enrollment enrollment = enrollRepository.findById(id).orElse(null);
        if (enrollment == null) {
            throw new GlobalException(ErrorCode.ENROLLMENT_NOT_FOUND);
        }
        if (!enrollment.getMember().getMemberId().equals(member.getMemberId())) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED_USER);
        }

        return enrollment;
    }


    /*
        봉사 활동 지원자 있는지 확인
     */
    public void existApplicant(Board board) {
        if (board.getApplicantCnt() != 0L) {
            throw new GlobalException(ErrorCode.UNABLE_DELETE_BOARD);
        }
    }



}
