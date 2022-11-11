package com.simbongsa.Backend.util;

import com.simbongsa.Backend.entity.Board;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.exception.ErrorCode;
import com.simbongsa.Backend.exception.GlobalException;
import com.simbongsa.Backend.repository.BoardRepository;
import com.simbongsa.Backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Check {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    /*
        멤버 확인
     */



    /*
        상품 존재 유무 확인
     */
    public Board isExist(Long boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);
        if (board == null) {
            throw new GlobalException(ErrorCode.BOARD_NOT_FOUND);
        }
        return board;
    }


    /*
        게시글 작성한 유저인지 확인
     */
    public void isAuthor(Member member) {
        // 관리자 테이블 나눌건지 회의 후 수정해야 함
        if (boardRepository.findByAuthor(member.getUsername()).isEmpty()) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED_USER);
        }
    }

}
