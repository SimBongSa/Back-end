package com.simbongsa.Backend.util;

import com.simbongsa.Backend.entity.*;
import com.simbongsa.Backend.exception.ErrorCode;
import com.simbongsa.Backend.exception.GlobalException;
import com.simbongsa.Backend.jwt.TokenProvider;
import com.simbongsa.Backend.repository.*;
import com.simbongsa.Backend.shared.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Check {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final EnrollRepository enrollRepository;

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    /*
        memberId로 멤버 존재 확인
     */
    public Member findMember(Long memberId) {
        Member member = memberRepository.findByMemberId(memberId).orElse(null);
        if (member == null) {
            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return member;
    }


    /*
        멤버 확인 (username 중복 유무)
     */
    public Member isPresentMember(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        return optionalMember.orElse(null);
    }

    /*
        멤버 확인 (member 가 null 일 때)
     */

    public void isNotMember(Member member) {
        if (null == member) {
            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
        }

    }

    /*
        멤버 확인 (동일 유무)
     */

    public void isSameMember(Member preMember, Member member, Long memberId) {

        if (!preMember.getMemberId().equals(memberId)) {
            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
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
        개인회원인지 확인
     */
    public void isMember(Member member) {
        if (member.getAuthority() != Authority.ROLE_MEMBER) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED_MEMBER);
        }
    }


    /*
        중복 확인
     */

    // Username 중복확인 (200 ErrorCode)
    public void isDuplicatedUsername(String username) {
        if (null != isPresentMember(username)) {
            throw new GlobalException(ErrorCode.DUPLICATE_USERNAME);
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
        DB에 있는 비밀번호와 새로 입력한 비밀번호의 일치 여부 확인
     */

    public void isMatched(String inputPassword, String dbPassword) {

        if(!passwordEncoder.matches(inputPassword, dbPassword)) {
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
        게시글 검색 시 게시글 리스트 존재 유무 확인
     */

    public void existBoardList(List<Board> boards) {
        if (boards.isEmpty()) {
            throw new GlobalException(ErrorCode.SEARCH_NOT_FOUND);
        }
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



    /*
        봉사 활동 지원자 있는지 확인
     */
    public void existApplicant(Board board) {
        if (board.getApplicantCnt() != 0L) {
            throw new GlobalException(ErrorCode.UNABLE_DELETE_BOARD);
        }
    }

    /*
        신청 여부 확인
     */
    public Enrollment isEnrolled(Long enrollId) {
        Enrollment enrollment = enrollRepository.findById(enrollId).orElse(null);

        if (enrollment == null) {
            throw new GlobalException(ErrorCode.ENROLLMENT_NOT_FOUND);
        }

        return enrollment;
    }

    public void myApplicant(Enrollment enrollment, Member member) {
        if (!enrollment.getBoard().getMember().getUsername().equals(member.getUsername())) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED_ENROLLMENT);
        }
    }

}
