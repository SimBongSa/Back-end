package com.simbongsa.Backend.dto.response;

import com.simbongsa.Backend.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponse {

    private Long boardId;

    private String author;

    private String content;

    private LocalDateTime createdAt;

    // Todo comment 게터, TimeStamped 상속, Board 연관 관계 필요
    public CommentResponse(Comment comment) {
//        this.boardId = comment.
    }
}
