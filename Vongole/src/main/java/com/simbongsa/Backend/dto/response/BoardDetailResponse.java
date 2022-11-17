package com.simbongsa.Backend.dto.response;

import com.simbongsa.Backend.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardDetailResponse {

    private Long boardId;

    private String author;

    private String title;

    private String content;

    private Long hits;

    private String boardImage;

    private LocalDate dueDay;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime createAt;

    private String area;

//    private String category;

    private Long applicantCnt;

    private List<CommentResponse> comments;

    public BoardDetailResponse(Board board, List<CommentResponse> commentResponses) {
        this.boardId = board.getId();
        this.author = board.getMember().getUsername();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.hits = board.getHits();
        this.boardImage = board.getBoardImage();
        this.dueDay = board.getDueDay();
        this.startDate = board.getStartDate();
        this.endDate = board.getEndDate();
        this.createAt = board.getCreatedAt();
        this.area = board.getArea();
//        this.category = board.getCategory();
        this.applicantCnt = board.getApplicantCnt();

        this.comments = commentResponses;
    }
}

