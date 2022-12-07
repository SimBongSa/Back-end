package com.simbongsa.Backend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simbongsa.Backend.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponse {

    private Long commentId;

    private Long boardId;

    private Long memberId;

    private String username;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public CommentResponse(Comment comment) {
        this.commentId = comment.getId();
        this.boardId = comment.getBoard().getId();
        this.memberId = comment.getMember().getMemberId();
        this.username = comment.getMember().getUsername();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
