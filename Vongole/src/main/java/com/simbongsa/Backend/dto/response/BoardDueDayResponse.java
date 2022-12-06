package com.simbongsa.Backend.dto.response;

import com.simbongsa.Backend.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class BoardDueDayResponse {

    private Long boardId;

    private Timestamp dueDay;

    public BoardDueDayResponse(Board board) {
        this.boardId=board.getId();
        this.dueDay=board.getDueDay();
    }
}