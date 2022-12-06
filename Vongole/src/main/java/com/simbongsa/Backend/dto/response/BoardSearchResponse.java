package com.simbongsa.Backend.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.simbongsa.Backend.entity.Tag;
import lombok.Data;

import java.util.List;

@Data
public class BoardSearchResponse {
    private Long boardId;
    private List<Tag> tags;

    // Todo Querydsl 관련 -> 곧 사용 예정

    @QueryProjection
    public BoardSearchResponse(Long boardId, List<Tag> tags) {
        this.boardId = boardId;
        this.tags = tags;
    }
}
