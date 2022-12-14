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

    public static ResponseEntity<ErrorResponse> toResponseEntity(BaseException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        String errorMessage;

        if (exception.getMessage() == null || exception.getMessage().equals("")) {
            errorMessage = errorCode.getMessage();
        } else {
            errorMessage = exception.getMessage();
        }

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(
                        ErrorResponse.builder()
                                .success(false)
                                .status(errorCode.getHttpStatus().name())
                                .code(errorCode.name())
                                .message(errorMessage)
                                .build()
                );
    }

}
