package com.example.musinsasample.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATE_REWARD(HttpStatus.BAD_REQUEST, "이미 보상을 지급 받았습니다."),
    REWARD_EXHAUSTED(HttpStatus.BAD_REQUEST, "오늘치 보상이 모두 소진되어 더 이상 받을 수 있는 보상이 없습니다."),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "올바르지 않은 입력입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
