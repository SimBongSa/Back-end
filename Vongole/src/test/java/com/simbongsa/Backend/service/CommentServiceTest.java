package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.request.CommentRequest;
import com.simbongsa.Backend.dto.response.CommentResponse;
import com.simbongsa.Backend.dto.response.MsgResponse;
import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.exception.GlobalException;
import com.simbongsa.Backend.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CommentService commentService;

    @Test
    @Transactional
    @DisplayName("코맨트생성 성공")
    void createComment(){
        //예상
        Member member = memberRepository.findByMemberId(66L).orElse(null);
        Long id = 11L;
        CommentRequest commentRequest = new CommentRequest("안녕하세요");
        //실제
        ResponseDto<CommentResponse> comment1 = commentService.createComment(member,id,commentRequest);
        String content = comment1.getData().getContent();
        //비교
        assertEquals(commentRequest.getContent(),content);
    }

    @Test
    @Transactional
    @DisplayName("코맨트생성 실패 없는 게시글에 코멘트 생성")
    void createComment1(){
        //예상
        Member member = memberRepository.findByMemberId(66L).orElse(null);
        System.out.println(member);
        Long id = 1000L;
        CommentRequest commentRequest = new CommentRequest("안녕하세요");
        //비교
        assertThrows(GlobalException.class,()->commentService.createComment(member,id,commentRequest));
    }

    @Test
    @Transactional
    @DisplayName("코맨트생성 실패 댓글 작성없이 댓글 생성")
    void createComment2(){
        //예상
        Member member = memberRepository.findByMemberId(66L).orElse(null);
        Long id = 1L;
        CommentRequest commentRequest = new CommentRequest("");
        CommentRequest commentRequest1 = new CommentRequest(null);
        //비교
        assertThrows(IllegalArgumentException.class,()->commentService.createComment(member,id,commentRequest));
        assertThrows(IllegalArgumentException.class,()->commentService.createComment(member,id,commentRequest1));
    }

    @Test
    @Transactional
    @DisplayName("코멘트수정 성공")
    void updateComment(){
        //예상
        Member member = memberRepository.findByMemberId(66L).orElse(null);
        Long id = 11L;
        CommentRequest commentRequest = new CommentRequest("안녕하세요");
        CommentRequest updateRequest = new CommentRequest("안녕?");
        //실제
        ResponseDto<CommentResponse> comment = commentService.createComment(member,id,commentRequest);
        ResponseDto<CommentResponse> comment1 = commentService.updateComment(member,comment.getData().getCommentId(), updateRequest);
        String content = comment1.getData().getContent();
        //비교
        assertEquals(updateRequest.getContent(),content);
    }

    @Test
    @Transactional
    @DisplayName("코멘트수정 실패 다른 코멘트를 수정")
    void updateComment1(){
        //예상
        Member member = memberRepository.findByMemberId(66L).orElse(null);
        Long id = 11L;
        CommentRequest commentRequest = new CommentRequest("안녕하세요");
        CommentRequest updateRequest = new CommentRequest("안녕?");
        //실제
        commentService.createComment(member,id,commentRequest);
        //비교
        assertThrows(GlobalException.class,()->commentService.updateComment(member,1L,updateRequest));
    }

    @Test
    @Transactional
    @DisplayName("코멘트수정 실패 다른 사람이 댓글 수정")
    void updateComment2(){
        //예상
        Member member1 = memberRepository.findByMemberId(66L).orElse(null);
        Member member2 = memberRepository.findByMemberId(67L).orElse(null);
        Long id = 11L;
        CommentRequest commentRequest = new CommentRequest("안녕하세요");
        CommentRequest updateRequest = new CommentRequest("안녕?");
        //실제
        ResponseDto<CommentResponse> comment = commentService.createComment(member1,id,commentRequest);
        //비교
        assertThrows(GlobalException.class,()->commentService.updateComment(member2,comment.getData().getCommentId(), updateRequest));
    }

    @Test
    @Transactional
    @DisplayName("코멘트 삭제 성공")
    void deleteComment(){
        //예상
        Member member = memberRepository.findByMemberId(66L).orElse(null);
        Long id = 11L;
        CommentRequest commentRequest = new CommentRequest("안녕하세요");
        String msg = "댓글 삭제 완료";
        //실제
        ResponseDto<CommentResponse> comment1 = commentService.createComment(member,id,commentRequest);
        ResponseDto<MsgResponse> comment = commentService.deleteComment(member,comment1.getData().getCommentId());
        String content = comment.getData().getMsg();
        //비교
        assertEquals(msg,content);
    }

    @Test
    @Transactional
    @DisplayName("코멘트 삭제 실패 한댓글에 다른 유저가 삭제하려고 함")
    void deleteComment1(){
        //예상
        Member member1 = memberRepository.findByMemberId(66L).orElse(null);
        Member member2 = memberRepository.findByMemberId(67L).orElse(null);
        Long id = 11L;
        CommentRequest commentRequest = new CommentRequest("안녕하세요");
        //실제
        ResponseDto<CommentResponse> comment1 = commentService.createComment(member1,id,commentRequest);
        //비교
        assertThrows(GlobalException.class,()->commentService.deleteComment(member2,comment1.getData().getCommentId()));
    }
}