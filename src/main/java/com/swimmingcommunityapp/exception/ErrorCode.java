package com.swimmingcommunityapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NICKNAME_DUPLICATION(HttpStatus.CONFLICT,"닉네임이 중복됩니다."),
    USERNAME_DUPLICATION(HttpStatus.CONFLICT,"아이디가 중복됩니다."),
    NICKNAME_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 닉네임입니다."),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 아이디입니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 카테고리입니다."),

    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"패스워드가 잘못되었습니다."),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED,"사용자가 권한이 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 게시글이 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 댓글이 없습니다."),

    LIKE_DUPLICATION(HttpStatus.CONFLICT,"이미 좋아요를 눌렀던 게시글입니다."),

    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB에러");

    private HttpStatus httpStatus;
    private String message;
}
