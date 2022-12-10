package com.example.musinsasample.domain.entity;

import com.example.musinsasample.domain.value.ConsecutiveDay;
import lombok.Getter;

import java.time.LocalDate;

public class RewardHistory {
    private Integer id;

    @Getter
    private final LocalDate date;
    private final Integer userId;

    @Getter
    private final Integer amount;

    @Getter
    private final ConsecutiveDay consecutiveDay;

    public RewardHistory(Integer id, LocalDate date, Integer userId, Integer amount, ConsecutiveDay consecutiveDay) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.amount = amount;
        this.consecutiveDay = consecutiveDay;
    }

    public RewardHistory(LocalDate date, Integer userId, Integer amount, ConsecutiveDay consecutiveDay) {
        this.date = date;
        this.userId = userId;
        this.amount = amount;
        this.consecutiveDay = consecutiveDay;
    }
}
