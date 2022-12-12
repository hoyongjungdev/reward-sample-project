package com.example.musinsasample.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "reward_counts")
@NoArgsConstructor
public class RewardCount {
    @Id
    LocalDate rewardDate;

    @Getter
    Integer rewardClaimed;

    public RewardCount(LocalDate rewardDate) {
        this.rewardDate = rewardDate;
        rewardClaimed = 0;
    }

    public void increaseRewardClaimed() {
        rewardClaimed += 1;
    }
}
