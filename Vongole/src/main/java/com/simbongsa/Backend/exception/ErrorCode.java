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
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Member Not Found", "해당 멤버는 존재하지 않습니다."),



    /* CONFLICT 409 error*/



    /*500 server error*/

    DUPLICATE_NICKNAME(HttpStatus.NOT_FOUND.value(), "Nickname is Duplicated", "이미 사용 중인 닉네임입니다."),

    PASSWORD_NOT_MATCHED(HttpStatus.NOT_FOUND.value(),"Password is Not Matched", "비밀번호가 일치하지 않습니다."),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "Token is Invalid", "토큰이 유효하지 않습니다.");



    private final Integer httpStatus;
    private final String message;
    private final String detail;

}