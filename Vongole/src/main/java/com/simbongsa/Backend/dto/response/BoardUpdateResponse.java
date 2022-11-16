package com.simbongsa.Backend.dto.response;

import com.simbongsa.Backend.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdateResponse {

    private String title;

    private String content;

    private String boardImage;

    private String dueDay;

    private String startDate;

    private String endDate;

    private String area;

//    private String category;

    public BoardUpdateResponse(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.boardImage = board.getBoardImage();
        this.dueDay = board.getDueDay();
        this.startDate = board.getStartDate();
        this.endDate = board.getEndDate();
        this.area = board.getArea();
//        this.category = board.getCategory();
    }
}
