package com.example.musinsasample.exception;

public class ValidationException extends BaseException {
    public ValidationException() {
        super(ErrorCode.VALIDATION_ERROR);
    }

    public ValidationException(String message) {
        super(ErrorCode.VALIDATION_ERROR, message);
    }
}
