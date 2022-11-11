package com.simbongsa.Backend.dto.response;

import com.simbongsa.Backend.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
public class BoardResponse {

    private String title;

    private String content;

    private Long count;

    private String boardImage;

    private String dueDay;

    private String startDate;

    private String endDate;

    private String area;

    private String category;

    public BoardResponse(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.count = board.getCount();
        this.boardImage = board.getBoardImage();
        this.dueDay = board.getDueDay();
        this.startDate = board.getStartDate();
        this.endDate = board.getEndDate();
        this.area = board.getArea();
        this.category = board.getCategory();
    }
}
