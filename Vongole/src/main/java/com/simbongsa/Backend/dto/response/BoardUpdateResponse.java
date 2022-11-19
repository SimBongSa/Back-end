package com.simbongsa.Backend.dto.response;

import com.simbongsa.Backend.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class BoardUpdateResponse {

    private String title;

    private String content;

    private String boardImage;

    private LocalDate dueDay;

    private LocalDate startDate;

    private LocalDate endDate;

    private String area;

    private String detailArea;

//    private String category;

    public BoardUpdateResponse(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.boardImage = board.getBoardImage();
        this.dueDay = board.getDueDay();
        this.startDate = board.getStartDate();
        this.endDate = board.getEndDate();
        this.area = board.getArea();
        this.detailArea = board.getDetailArea();
//        this.category = board.getCategory();
    }
}
