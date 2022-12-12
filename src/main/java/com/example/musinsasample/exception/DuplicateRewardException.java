package com.example.musinsasample.exception;

public class DuplicateRewardException extends BaseException {
    public DuplicateRewardException() {
        super(ErrorCode.DUPLICATE_REWARD);
    }
}
