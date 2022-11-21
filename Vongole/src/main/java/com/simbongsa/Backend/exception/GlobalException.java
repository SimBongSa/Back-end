package com.simbongsa.Backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

@Getter
@AllArgsConstructor
public class GlobalException extends RuntimeException {

    private final ErrorCode errorCode;


}
