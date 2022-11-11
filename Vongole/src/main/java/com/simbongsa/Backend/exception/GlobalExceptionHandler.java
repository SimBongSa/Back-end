package com.simbongsa.Backend.exception;

import com.simbongsa.Backend.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    protected ResponseDto<?> handleGlobalException(GlobalException e) {
        return ResponseDto.fail(
                e.getErrorCode().getHttpStatus(),
                e.getErrorCode().getMessage(),
                e.getErrorCode().getDetail());
    }
}
