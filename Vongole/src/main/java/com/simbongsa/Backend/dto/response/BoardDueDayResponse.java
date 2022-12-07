package com.simbongsa.Backend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simbongsa.Backend.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class BoardDueDayResponse {

    private Long boardId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp dueDay;

    public BoardDueDayResponse(Board board) {
        this.boardId=board.getId();
        this.dueDay=board.getDueDay();
    }
}
