package com.simbongsa.Backend.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* OK 200 error*/

    DUPLICATE_USERNAME(HttpStatus.OK.value(), "Username is Duplicated", "사용 불가능한 아이디 입니다."),

    SEARCH_NOT_FOUND(HttpStatus.OK.value(), "Search Result Not Found", "검색 결과가 없습니다."),

    /* BAD_REQUEST 400 error*/

    // 로그인
    LOGIN_USERNAME_EMPTY(HttpStatus.BAD_REQUEST.value(), "Login Username is Empty", "아이디를 입력해주세요"),
    LOGIN_PASSWORD_EMPTY(HttpStatus.BAD_REQUEST.value(), "Login Password is Empty", "비밀번호를 입력해주세요"),
    USERNAME_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "Username Not Found","해당 아이디는 존재하지 않습니다."),
    LOGIN_PASSWORD_NOT_MATCHED(HttpStatus.BAD_REQUEST.value(), "Login Password Not Matched", "비밀번호가 틀립니다."),



    /*UNAUTHORIZED 401 error*/

    // 토큰
    WRONG_TOKEN(HttpStatus.UNAUTHORIZED.value(), "Wrong Token", "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED.value(), "Token Expired", "만료된 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "Unsupported Token", "지원되지 않는 토큰입니다."),

    UNAUTHORIZED_AUTHOR(HttpStatus.UNAUTHORIZED.value(), "Unauthorized Author", "작성자만 수정 또는 삭제할 수 있습니다."),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED.value(), "Unauthorized User", "관리자 회원이 아닙니다."),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED.value(), "Unauthorized User", "개인 회원이 아닙니다."),

    UNAUTHORIZED_ENROLLMENT(HttpStatus.UNAUTHORIZED.value(), "Unauthorized enrollment", "봉사 활동 승인 권한이 없습니다."),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "Token is Invalid", "토큰이 유효하지 않습니다."),

    ALREADY_APPLIED(HttpStatus.UNAUTHORIZED.value(), "Board is Already Applied", "이미 신청 중인 게시물입니다."),

    /*FORBIDDEN 403 error*/
    // 나중에 에러코드 명 바꾸기
    UNABLE_DELETE_BOARD(HttpStatus.FORBIDDEN.value(), "Unable To Delete Board", "봉사활동 지원자가 있기 때문에 삭제할 수 없습니다."),

    /*Not Found 404 error*/
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Board Not Found", "해당 게시물은 존재하지 않습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Comment Not Found", "해당 댓글은 존재하지 않습니다."),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND.value(),"Token Not Found","해당 토큰은 존재하지 않습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Member Not Found", "해당 멤버는 존재하지 않습니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Username Not Found", "해당 아이디는 존재하지 않습니다."),

    ENROLLMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Applicant Not Found", "신청자를 찾을 수 없습니다."),


    /* webSocket 관련 */
    NO_SUCH_CHATROOM(HttpStatus.NOT_FOUND.value(), "No such Chatroom", "존재하지 않는 채팅방 입니다.(또는 잘못된 채팅방 ID 입니다.)"),
    NO_SUCH_METHOD(HttpStatus.NOT_FOUND.value(), "No such Method", "알 수 없는 요청 입니다."),

    /*500 server error*/

    DUPLICATE_NICKNAME(HttpStatus.NOT_FOUND.value(), "Nickname is Duplicated", "이미 사용 중인 닉네임입니다."),

    PASSWORD_NOT_MATCHED(HttpStatus.NOT_FOUND.value(),"Password is Not Matched", "비밀번호가 일치하지 않습니다.");






    private final Integer httpStatus;
    private final String message;
    private final String detail;

}
