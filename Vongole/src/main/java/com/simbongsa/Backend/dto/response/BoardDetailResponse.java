package com.simbongsa.Backend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simbongsa.Backend.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardDetailResponse {

    private Long boardId;

    private String author;

    private Long authorId;

    private String profileImage;

    private String title;

    private String content;

    private Long hits;

    private String boardImage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp dueDay;

    private LocalDate startDate;

    private LocalDate endDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createAt;

    private String area;

    private String detailArea;

    private Long applicantCnt;

//    private boolean isEnrolled;

    private List<CommentResponse> comments;

    private List<String> tags;

    private List<String> applicants;

    public BoardDetailResponse(Board board, List<CommentResponse> commentResponses, List<String> tags, List<String> applicants) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.author = board.getMember().getUsername();
        this.authorId = board.getMember().getMemberId();
        this.profileImage = board.getMember().getProfileImage();
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
        this.applicants = applicants;
    }
}

