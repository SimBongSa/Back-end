package com.simbongsa.Backend.entity;

import com.simbongsa.Backend.dto.request.CommentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //기관,멤버
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Board board;

    @Column(nullable = false)
    private String content;

    public void update(CommentRequest commentRequest) {
        this.content = commentRequest.getContent() != null ? commentRequest.getContent() :this.content;
    }

    public Comment(Member member, Board board, CommentRequest commentRequest) {
        if(commentRequest.getContent()==null||commentRequest.getContent() .equals("")){
            throw new IllegalArgumentException("댓글생성 실패 쓴댓글이 없습니다.");
        }
        this.member = member;
        this.board = board;
        this.content = commentRequest.getContent();
    }
}
