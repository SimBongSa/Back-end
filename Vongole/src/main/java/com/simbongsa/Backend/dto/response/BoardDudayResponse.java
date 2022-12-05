package com.simbongsa.Backend.dto.response;

import com.simbongsa.Backend.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class BoardDudayResponse {

    private Long boardId;

    private Timestamp dueDay;

    public BoardDudayResponse(Board board) {
        this.boardId=board.getId();
        this.dueDay=board.getDueDay();
    }
}
