package com.simbongsa.Backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Approval {

    WAITING, // 승인 대기
    PASS, // 승인
    FAIL; // 거절

}
