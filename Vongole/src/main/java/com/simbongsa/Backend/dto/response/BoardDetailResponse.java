package com.simbongsa.Backend.dto.response;

import com.simbongsa.Backend.entity.Board;
import com.simbongsa.Backend.entity.Tag;
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

    private String detailArea;

    private Long applicantCnt;

    private boolean isEnrolled;

    private List<CommentResponse> comments;

    private List<Tag> tags;

    public BoardDetailResponse(Board board, List<CommentResponse> commentResponses, List<Tag> tags) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.author = board.getMember().getUsername();
        this.content = board.getContent();
        this.hits = board.getHits();
        this.boardImage = board.getBoardImage();
        this.dueDay = board.getDueDay();
        this.startDate = board.getStartDate();
        this.endDate = board.getEndDate();
        this.area = board.getArea();
        this.detailArea = board.getDetailArea();
        this.createAt = board.getCreatedAt();
        this.applicantCnt = board.getApplicantCnt();

        this.comments = commentResponses;
        this.tags = tags;
    }
}

