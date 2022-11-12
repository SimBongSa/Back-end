package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.request.CommentRequest;
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

    private CommentRepository commentRepository;

    private final Check check;
    public ResponseDto<MsgResponse> createComment(Member member, Long id, CommentRequest commentRequest) {
        //멤버,컴퍼니 체크

        //게시글 체크
        Board board = check.isExist(id);
        //entity생성후 db저장
        Comment comment = new Comment(board,commentRequest);
        commentRepository.save(comment);

        return ResponseDto.success(new MsgResponse("댓글 생성 완료"));
    }

    public ResponseDto<MsgResponse> updateComment(Member member, Long id, CommentRequest commentRequest) {
        //멤버 컴퍼니 체크

        //댓글 체크
        Comment comment = check.isComment(id);
        //entity수정후 db저장
        comment.update(commentRequest);
        commentRepository.save(comment);

        return ResponseDto.success(new MsgResponse("댓글 수정 완료"));
    }

    public ResponseDto<MsgResponse> deleteComment(Member member, Long id) {
        //멤버 컴퍼니 체크

        //댓글 체크
        Comment comment = check.isComment(id);
        //댓글 삭제
        commentRepository.delete(comment);

        return ResponseDto.success(new MsgResponse("댓글 삭제 완료"));
    }
}
