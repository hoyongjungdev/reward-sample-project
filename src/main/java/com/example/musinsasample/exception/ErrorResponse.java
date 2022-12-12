package com.example.musinsasample.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponse {
    private final boolean success;
    private final String status;
    private final String code;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(
                        ErrorResponse.builder()
                                .success(false)
                                .status(errorCode.getHttpStatus().name())
                                .code(errorCode.name())
                                .message(errorCode.getMessage())
                                .build()
                );
    }

}
