package com.example.musinsasample.domain.entity;

import com.example.musinsasample.domain.value.ConsecutiveDay;
import com.example.musinsasample.exception.ValidationException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    private String username;

    @Getter
    private Integer point;

    private static final int MAX_USERNAME_LENGTH = 50;

    private final static Map<Integer, Integer> rewardForConsecutive = new HashMap<>(Map.of(
            3, 300,
            5, 500,
            10, 1000
    ));

    private final static int DEFAULT_REWARD_AMOUNT = 100;

    protected User() {
    }

    public User(Integer id, String username, Integer point) {
        if (username.equals("")) {
            throw new ValidationException("아이디가 입력되지 않았습니다.");
        }

        if (username.length() > MAX_USERNAME_LENGTH) {
            throw new ValidationException(String.format("아이디가 %d자를 초과했습니다", MAX_USERNAME_LENGTH));
        }

        this.id = id;
        this.username = username;
        this.point = point;
    }

    public User(String username) {
        this(null, username, 0);
    }

    public RewardHistory receiveFirstReward(LocalDateTime dateTime) {
        return rewardWithDayOne(dateTime);
    }

    public RewardHistory receiveRewardWithHistory(RewardHistory recentReward, LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();

        if (recentReward.getDate().plusDays(1).isEqual(date)) {
            ConsecutiveDay consecutiveDay = recentReward.getConsecutiveDay().nextDay();

            int amount = calculateRewardAmount(consecutiveDay);
            point += amount;

            return new RewardHistory(dateTime, username, amount, consecutiveDay);
        } else {
            return rewardWithDayOne(dateTime);
        }
    }

    private RewardHistory rewardWithDayOne(LocalDateTime dateTime) {
        ConsecutiveDay day = new ConsecutiveDay(1);

        int amount = calculateRewardAmount(day);
        point += amount;

        return new RewardHistory(dateTime, username, amount, day);
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
