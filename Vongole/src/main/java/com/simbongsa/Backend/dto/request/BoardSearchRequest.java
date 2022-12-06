package com.simbongsa.Backend.dto.request;

import com.querydsl.core.annotations.QueryProjection;
import com.simbongsa.Backend.entity.Tag;
import lombok.Data;

import java.util.List;

@Data
public class BoardSearchRequest {

    private Tag tag;
    private String startDate;
    private String endDate;
    private String area;


    // Todo Querydsl 관련 -> 곧 사용 예정

    @QueryProjection
    public BoardSearchRequest(Tag tag, String startDate, String endDate, String area) {
        this.tag = tag;
        this.startDate = startDate;
        this.endDate = endDate;
        this.area = area;
    }
}



