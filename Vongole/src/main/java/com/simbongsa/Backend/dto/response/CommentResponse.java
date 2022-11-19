package com.simbongsa.Backend.dto.response;

import com.simbongsa.Backend.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponse {

    private Long commentId;

    private Long boardId;

    private String nickname;

    private String content;

    private LocalDateTime createdAt;

    public CommentResponse(Comment comment) {
        this.commentId = comment.getId();
        this.boardId = comment.getBoard().getId();
        this.nickname = comment.getMember().getNickname();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
