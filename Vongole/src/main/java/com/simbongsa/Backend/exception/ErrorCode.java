package com.simbongsa.Backend.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* BAD_REQUEST 400 error*/


    /*UNAUTHORIZED 401 error*/
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED.value(), "Unauthorized User", "작성자만 수정 또는 삭제할 수 있습니다."),

    /*Not Found 404 error*/
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Board Not Found", "해당 게시물은 존재하지 않습니다."),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND.value(),"Token Not Found","해당 토큰은 존재하지 않습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Member Not Found", "해당 멤버는 존재하지 않습니다.");

    /* CONFLICT 409 error*/



    /*500 server error*/



    private final Integer httpStatus;
    private final String message;
    private final String detail;

}
