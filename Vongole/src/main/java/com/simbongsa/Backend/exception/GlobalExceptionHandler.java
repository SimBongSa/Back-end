package com.simbongsa.Backend.exception;

import com.simbongsa.Backend.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    protected ResponseEntity<?> handleGlobalException(GlobalException e) {


        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ResponseDto.fail(
                        e.getErrorCode().getHttpStatus(),
                        e.getErrorCode().getMessage(),
                        e.getErrorCode().getDetail())
        );
    }
}
