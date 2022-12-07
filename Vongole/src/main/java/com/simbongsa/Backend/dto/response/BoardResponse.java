package com.simbongsa.Backend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simbongsa.Backend.entity.Board;
import com.simbongsa.Backend.entity.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardResponse {

    private Long boardId;

    private String title;

    private String author;

//    private String content;

//    private Long hits;

    private String boardImage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp dueDay;

    private LocalDate startDate;

    private LocalDate endDate;

    private String area;

    private String detailArea;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private List<String> tags;

    private Long applicantCnt;

    public BoardResponse(Board board, List<String> tags) {
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
        this.applicantCnt = board.getApplicantCnt();
        this.tags = tags;
    }

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
        this.applicantCnt = board.getApplicantCnt();
    }

}
