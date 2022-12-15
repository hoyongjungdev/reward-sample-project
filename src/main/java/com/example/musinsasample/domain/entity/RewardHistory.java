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
import java.time.LocalDateTime;

@Entity
@Table(name = "reward_histories")
public class RewardHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    private LocalDateTime issuedAt;

    @Getter
    private String username;

    @Getter
    private Integer amount;

    @Getter
    @Embedded
    private ConsecutiveDay consecutiveDay;

    protected RewardHistory() {
    }

    public RewardHistory(LocalDateTime issuedAt, String username, Integer amount, ConsecutiveDay consecutiveDay) {
        this.issuedAt = issuedAt;
        this.username = username;
        this.amount = amount;
        this.consecutiveDay = consecutiveDay;
    }

    public LocalDate getDate() {
        return issuedAt.toLocalDate();
    }
}
