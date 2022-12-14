package com.example.musinsasample.domain.entity;

import com.example.musinsasample.domain.value.ConsecutiveDay;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;

    @Getter
    private Integer point;

    private final static Map<Integer, Integer> rewardForConsecutive = new HashMap<>(Map.of(
            3, 300,
            5, 500,
            10, 1000
    ));

    private final static int DEFAULT_REWARD_AMOUNT = 100;

    protected User() {
    }

    public User(Integer id, String username, Integer point) {
        this.id = id;
        this.username = username;
        this.point = point;
    }

    public User(String username) {
        this.username = username;
        this.point = 0;
    }

    public RewardHistory receiveFirstReward(LocalDate date) {
        return rewardWithDayOne(date);
    }

    public RewardHistory receiveRewardWithHistory(RewardHistory recentReward, LocalDate date) {
        if (recentReward.getRewardDate().plusDays(1).isEqual(date)) {
            ConsecutiveDay consecutiveDay = recentReward.getConsecutiveDay().nextDay();

            int amount = calculateRewardAmount(consecutiveDay);
            point += amount;

            return new RewardHistory(date, username, amount, consecutiveDay);
        } else {
            return rewardWithDayOne(date);
        }
    }

    private RewardHistory rewardWithDayOne(LocalDate date) {
        ConsecutiveDay day = new ConsecutiveDay(1);

        int amount = calculateRewardAmount(day);
        point += amount;

        return new RewardHistory(date, username, amount, day);
    }

    private int calculateRewardAmount(ConsecutiveDay consecutiveDay) {
        int amount = DEFAULT_REWARD_AMOUNT;
        int dayValue = consecutiveDay.getDay();

        if (rewardForConsecutive.containsKey(dayValue)) {
            amount += rewardForConsecutive.get(dayValue);
        }

        return amount;
    }
}
