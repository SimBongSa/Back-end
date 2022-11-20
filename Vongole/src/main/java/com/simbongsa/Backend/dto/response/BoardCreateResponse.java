package com.simbongsa.Backend.dto.response;

import lombok.Getter;

@Getter
public class BoardCreateResponse {

    private Long boardId;
    private String msg;

    public BoardCreateResponse(Long boardId, String msg) {
        this.boardId = boardId;
        this.msg = msg;
    }
}
