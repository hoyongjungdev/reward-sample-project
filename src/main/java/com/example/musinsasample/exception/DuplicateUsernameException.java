package com.example.musinsasample.exception;

public class DuplicateUsernameException extends BaseException {
    public DuplicateUsernameException() {
        super(ErrorCode.DUPLICATE_USERNAME);
    }
}
