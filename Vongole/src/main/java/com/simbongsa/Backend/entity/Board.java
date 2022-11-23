package com.simbongsa.Backend.entity;

import com.simbongsa.Backend.dto.request.BoardRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private Long hits;

    @Column(nullable = false)
    private String boardImage;

    @Column(nullable = false)
    private LocalDate dueDay;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String area;

    @Column(nullable = false)
    private String detailArea;

    @Column
    private Long applicantCnt;


    public Board(BoardRequest boardRequest, Member member, String boardImage) {
        this.title = boardRequest.getTitle();
        this.content = boardRequest.getContent();
        this.member = member;
        this.boardImage = boardImage;
        this.dueDay = LocalDate.parse(boardRequest.getDueDay(),DateTimeFormatter.ISO_LOCAL_DATE);
        this.startDate = LocalDate.parse(boardRequest.getStartDate(),DateTimeFormatter.ISO_LOCAL_DATE);
        this.endDate = LocalDate.parse(boardRequest.getEndDate(),DateTimeFormatter.ISO_LOCAL_DATE);
        this.area = boardRequest.getArea();
        this.detailArea = boardRequest.getDetailArea();

        this.hits = 0L;
        this.applicantCnt = 0L;
    }

    public void update(BoardRequest boardRequest, String boardImage) {
        this.title = boardRequest.getTitle();
        this.content = boardRequest.getContent();
        this.boardImage = boardImage;
        this.dueDay = LocalDate.parse(boardRequest.getDueDay(),DateTimeFormatter.ISO_LOCAL_DATE);
        this.startDate = LocalDate.parse(boardRequest.getStartDate(),DateTimeFormatter.ISO_LOCAL_DATE);
        this.endDate = LocalDate.parse(boardRequest.getEndDate(),DateTimeFormatter.ISO_LOCAL_DATE);
        this.area = boardRequest.getArea();
        this.detailArea = boardRequest.getDetailArea();
    }

    // 조회수 증가
    public void addHits() {
        this.hits++;
    }

    public void addApplicant() {
        this.applicantCnt++;
    }

    public void removeApplicant() {
        this.applicantCnt--;
    }
}

