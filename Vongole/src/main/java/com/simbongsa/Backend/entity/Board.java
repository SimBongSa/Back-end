package com.simbongsa.Backend.entity;

import com.simbongsa.Backend.dto.request.BoardRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;


//    // 연관관계 맺을거면 빼고, 안맺을거면 넣어야함
//    @Column(nullable = false)
//    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private Long hits;

    @Column(nullable = false)
    private String boardImage;

    @Column(nullable = false)
    private String dueDay;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    @Column(nullable = false)
    private String area;

    @Column(nullable = false)
    private String category;

    public Board(BoardRequest boardRequest, Member member, String boardImage) {
        this.title = boardRequest.getTitle();
        this.content = boardRequest.getContent();
        this.member = member;
        this.boardImage = boardImage;
        this.dueDay = boardRequest.getDueDay();
        this.startDate = boardRequest.getStartDate();
        this.endDate = boardRequest.getEndDate();
        this.area = boardRequest.getArea();
        this.category = boardRequest.getCategory();

        this.hits = 0L;

    }

    public void update(BoardRequest boardRequest, String boardImage) {
        this.title = boardRequest.getTitle();
        this.content = boardRequest.getContent();
        this.boardImage = boardImage;
        this.dueDay = boardRequest.getDueDay();
        this.startDate = boardRequest.getStartDate();
        this.endDate = boardRequest.getEndDate();
        this.area = boardRequest.getArea();
        this.category = boardRequest.getCategory();
    }

    // 조회수 증가
    public void addHits() {
        this.hits++;
    }
}
