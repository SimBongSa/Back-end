package com.simbongsa.Backend.dto.response;

import com.simbongsa.Backend.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardDetailResponse {

    private Long boardId;

    private String author;

    private String title;

    private String content;

    private Long hits;

    private String boardImage;

    private String dueDay;

    private String startDate;

    private String endDate;

    private LocalDateTime createAt;

    private String area;

    private String category;

    public BoardDetailResponse(Board board) {
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
        this.category = board.getCategory();
    }
}

