package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.request.CommentRequest;
import com.simbongsa.Backend.dto.response.CommentResponse;
import com.simbongsa.Backend.dto.response.MsgResponse;
import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.entity.Board;
import com.simbongsa.Backend.entity.Comment;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.repository.CommentRepository;
import com.simbongsa.Backend.util.Check;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final Check check;

    public ResponseDto<CommentResponse> createComment(Member member, Long id, CommentRequest commentRequest) {
        //게시글 체크
        Board board = check.existBoard(id);
        //entity 생성 후 db 저장
        Comment comment = new Comment(member,board,commentRequest);
        commentRepository.save(comment);

        return ResponseDto.success(new CommentResponse(comment));
    }

    public ResponseDto<CommentResponse> updateComment(Member member, Long id, CommentRequest commentRequest) {
        //멤버,댓글 체크
        Comment comment = check.isComment(member,id);
        //entity수정후 db저장
        comment.update(commentRequest);
        commentRepository.save(comment);

        return ResponseDto.success(new CommentResponse(comment));
    }

    public ResponseDto<MsgResponse> deleteComment(Member member, Long id) {
        //멤버 컴퍼니 체크

        //댓글 체크
        Comment comment = check.isComment(member,id);
        //댓글 삭제
        commentRepository.delete(comment);

        return ResponseDto.success(new MsgResponse("댓글 삭제 완료"));
    }
}
