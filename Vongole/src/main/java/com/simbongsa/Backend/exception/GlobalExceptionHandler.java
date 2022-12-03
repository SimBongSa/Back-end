package com.simbongsa.Backend.exception;

import com.amazonaws.services.s3.model.AmazonS3Exception;
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


//    // Todo 조금 더 알아보고 수정하기
//    @ExceptionHandler({AmazonS3Exception.class})
//    protected ResponseEntity<?> handleMultipartException(AmazonS3Exception e) {
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//
//    }

}

