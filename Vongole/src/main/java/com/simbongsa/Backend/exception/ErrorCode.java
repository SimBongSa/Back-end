package com.simbongsa.Backend.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* BAD_REQUEST 400 error*/


    /*UNAUTHORIZED 401 error*/
    UNAUTHORIZED_AUTHOR(HttpStatus.UNAUTHORIZED.value(), "Unauthorized Author", "작성자만 수정 또는 삭제할 수 있습니다."),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED.value(), "Unauthorized User", "관리자만 게시물을 생성할 수 있습니다."),

    /*FORBIDDEN 403 error*/
    // 나중에 에러코드 명 바꾸기
    UNABLE_DELETE_BOARD(HttpStatus.FORBIDDEN.value(), "Unable To Delete Board", "봉사활동 지원자가 있기 때문에 삭제할 수 없습니다."),

    /*Not Found 404 error*/
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Board Not Found", "해당 게시물은 존재하지 않습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Comment Not Found", "해당 댓글은 존재하지 않습니다."),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND.value(),"Token Not Found","해당 토큰은 존재하지 않습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Member Not Found", "해당 멤버는 존재하지 않습니다."),

    ENROLLMENT_NOT_FOUND(HttpStatus.NO_CONTENT.value(), "Applicant Not Found", "신청자를 찾을 수 없습니다."),


    /* CONFLICT 409 error*/



    /*500 server error*/

    DUPLICATE_NICKNAME(HttpStatus.NOT_FOUND.value(), "Nickname is Duplicated", "이미 사용 중인 닉네임입니다."),

    PASSWORD_NOT_MATCHED(HttpStatus.NOT_FOUND.value(),"Password is Not Matched", "비밀번호가 일치하지 않습니다."),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "Token is Invalid", "토큰이 유효하지 않습니다."),

    ALREADY_APPLIED(HttpStatus.UNAUTHORIZED.value(), "Board is Already Applied", "이미 신청 중인 게시물입니다.");



    private final Integer httpStatus;
    private final String message;
    private final String detail;

}
