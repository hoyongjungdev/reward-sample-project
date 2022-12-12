package com.example.musinsasample.exception;

public class RewardExhaustedException extends BaseException {
    public RewardExhaustedException() {
        super(ErrorCode.REWARD_EXHAUSTED);
    }
}
