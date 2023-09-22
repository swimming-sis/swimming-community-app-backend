package com.swimmingcommunityapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NICKNAME_DUPLICATION(HttpStatus.CONFLICT,"닉네임이 중복됩니다."),
    USERNAME_DUPLICATION(HttpStatus.CONFLICT,"아이디가 중복됩니다."),
    SWIMMINGPOOL_DUPLICATION(HttpStatus.CONFLICT,"수영장 데이터가 중복됩니다."),
    WRONG_FILE_FORMAT(HttpStatus.CONFLICT,"잘못된 파일 형태입니다."),
    FILE_UPLOAD_ERROR(HttpStatus.CONFLICT,"파일 업로드 중 오류가 생겼습니다." ),
    NICKNAME_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 닉네임입니다."),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 아이디입니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 카테고리입니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 댓글입니다."),
    SWIMMINGPOOL_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 수영장 데이터입니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 리뷰입니다."),
    PHONENUMBER_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 핸드폰 번호입니다."),
    TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 태그입니다."),
    REVIEWTAG_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 리뷰와 태그입니다." ),
    LOG_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 일지입니다." ),
    FILE_NOT_EXISTS(HttpStatus.NOT_FOUND,"비어있는 파일입니다." ),

    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"패스워드가 잘못되었습니다."),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED,"사용자가 권한이 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 게시글이 없습니다."),

    LIKE_DUPLICATION(HttpStatus.CONFLICT,"이미 좋아요를 눌렀던 게시글입니다."),
    LIKE_CANCEL_DUPLICATION(HttpStatus.CONFLICT,"이미 좋아요 취소를 눌렀던 게시글입니다."),
    NUMBER_CONFLICT(HttpStatus.CONFLICT,"인증번호가 일치하지 않습니다."),

    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB에러");

    private HttpStatus httpStatus;
    private String message;
}
