package com.simbongsa.Backend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MsgResponse {

    private String msg;

    public MsgResponse(String msg) {
        this.msg = msg;
    }
}
