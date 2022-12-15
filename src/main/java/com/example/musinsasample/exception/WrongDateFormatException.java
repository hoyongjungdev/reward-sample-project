package com.example.musinsasample.exception;

public class WrongDateFormatException extends BaseException {
    public WrongDateFormatException() {
        super(ErrorCode.WRONG_DATE_FORMAT);
    }
}
