package com.simbongsa.Backend.entity;

import com.simbongsa.Backend.dto.request.CommentRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //기관,멤버
    @Column
    private String author;

    @ManyToOne
    private Board board;

    @Column(nullable = false)
    private String content;

    public Comment(Board board, CommentRequest commentRequest) {
        this.board = board;
        this.content = commentRequest.getContent();
    }

    public void update(CommentRequest commentRequest) {
        this.content = commentRequest.getContent() != null ? commentRequest.getContent() :this.content;
    }
}
