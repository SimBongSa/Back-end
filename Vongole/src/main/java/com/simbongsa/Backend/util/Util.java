package com.simbongsa.Backend.util;

import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Util {

    private final MemberRepository memberRepository;

    public Member getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));
        return member;
    }


//    public Board getCurrentBoard(Long boardId) {
//
//        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("존재하지 않는 게시글입니다."));
//        return board;
//    }

//    public boolean checkBoardMember(Board board, Member member) {
//
//        if(board.getMember().getMemberId().equals(member.getMemberId())){
//            return false;
//        }
//        return true;
//    }
}
