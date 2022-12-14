package com.simbongsa.Backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

    @NotEmpty(message = "빈 칸을 채워 주세요.")
    private String content;

}

