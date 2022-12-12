package com.example.musinsasample.domain.entity;

import com.example.musinsasample.exception.RewardExhaustedException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Table(name = "reward_counts")
public class RewardCount {
    @Id
    LocalDate rewardDate;

    @Getter
    Integer rewardClaimed;

    private final static int MAXIMUM_USER_PER_DAY = 10;

    protected RewardCount() {
    }

    public RewardCount(LocalDate rewardDate) {
        this.rewardDate = rewardDate;
        rewardClaimed = 0;
    }

    public void increaseRewardClaimed() {
        if (rewardClaimed >= MAXIMUM_USER_PER_DAY) {
            throw new RewardExhaustedException();
        }

        rewardClaimed += 1;
    }
}
