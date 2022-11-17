package com.simbongsa.Backend.dto.response;

import com.simbongsa.Backend.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponse {

    private Long boardId;

    private String title;

    private String author;

//    private String content;

//    private Long hits;

    private String boardImage;

    private LocalDate dueDay;

    private LocalDate startDate;

    private LocalDate endDate;

    private String area;

    private String detailArea;

    private LocalDateTime createdAt;

//    private String category;

    private Long applicantCnt;

    public BoardResponse(Board board) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.author = board.getMember().getUsername();
//        this.content = board.getContent();
//        this.hits = board.getHits();
        this.boardImage = board.getBoardImage();
        this.dueDay = board.getDueDay();
        this.startDate = board.getStartDate();
        this.endDate = board.getEndDate();
        this.area = board.getArea();
        this.detailArea = board.getDetailArea();
        this.createdAt = board.getCreatedAt();
//        this.category = board.getCategory();
        this.applicantCnt = board.getApplicantCnt();
    }

}
