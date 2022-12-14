package com.example.musinsasample.domain.entity;

import com.example.musinsasample.domain.value.ConsecutiveDay;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Table(name = "reward_histories")
public class RewardHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    private LocalDate rewardDate;
    private String username;

    @Getter
    private Integer amount;

    @Getter
    @Embedded
    private ConsecutiveDay consecutiveDay;

    protected RewardHistory() {
    }

    public RewardHistory(LocalDate rewardDate, String username, Integer amount, ConsecutiveDay consecutiveDay) {
        this.rewardDate = rewardDate;
        this.username = username;
        this.amount = amount;
        this.consecutiveDay = consecutiveDay;
    }
}
