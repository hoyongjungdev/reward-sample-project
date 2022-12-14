package com.example.musinsasample.unit;

import com.example.musinsasample.domain.entity.RewardHistory;
import com.example.musinsasample.domain.entity.User;
import com.example.musinsasample.domain.value.ConsecutiveDay;
import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class RewardCalculationTests {

    @Test
    void WhenUserGetFirstReward_PointIs100AndDayIs1() {
        User user = new User(1, "my_user", 0);

        RewardHistory history = user.receiveFirstReward(LocalDate.of(2022, 12, 1));

        assertThat(user.getPoint()).isEqualTo(100);
        assertThat(history.getConsecutiveDay()).isEqualTo(new ConsecutiveDay(1));
        assertThat(history.getAmount()).isEqualTo(100);
    }

    @Test
    void WhenUserGetNonconsecutiveSecondReward_PointIs100AndDayIs1() {
        User user = new User(1, "my_user", 100);
        RewardHistory rewardHistory = new RewardHistory(
                LocalDate.of(2022, 11, 5),
                "my_user",
                100,
                new ConsecutiveDay(1)
        );

        RewardHistory history = user.receiveRewardWithHistory(
                rewardHistory,
                LocalDate.of(2022, 12, 1)
        );

        assertThat(user.getPoint()).isEqualTo(200);
        assertThat(history.getConsecutiveDay()).isEqualTo(new ConsecutiveDay(1));
        assertThat(history.getAmount()).isEqualTo(100);
    }

    @Test
    void WhenUserGetConsecutiveThirdReward_PointIs300AndDayIs3() {
        User user = new User(1, "my_user", 200);
        RewardHistory rewardHistory = new RewardHistory(
                LocalDate.of(2022, 11, 5),
                "my_user",
                100,
                new ConsecutiveDay(2)
        );

        RewardHistory history = user.receiveRewardWithHistory(
                rewardHistory,
                LocalDate.of(2022, 11, 6)
        );

        assertThat(user.getPoint()).isEqualTo(600);
        assertThat(history.getConsecutiveDay()).isEqualTo(new ConsecutiveDay(3));
        assertThat(history.getAmount()).isEqualTo(400);
    }

    @Test
    void WhenUserBreaksConsecutiveInFourthDay_PointIs100AndDayIs1() {
        User user = new User(1, "my_user", 1500);
        RewardHistory rewardHistory = new RewardHistory(
                LocalDate.of(2022, 11, 30),
                "my_user",
                400,
                new ConsecutiveDay(3)
        );

        RewardHistory history = user.receiveRewardWithHistory(
                rewardHistory,
                LocalDate.of(2022, 12, 2)
        );

        assertThat(user.getPoint()).isEqualTo(1600);
        assertThat(history.getConsecutiveDay()).isEqualTo(new ConsecutiveDay(1));
        assertThat(history.getAmount()).isEqualTo(100);
    }

    @Test
    void testUserReceivesRewardsConsecutivelyFor4And7And13Days() {
        User user = new User(1, "my_user", 1500);

        RewardHistory history = receiveRewardForConsecutiveDays(
                user,
                LocalDate.of(2022, 10, 3),
                4,
                null
        );

        history = receiveRewardForConsecutiveDays(
                user,
                LocalDate.of(2022, 11, 7),
                7,
                history
        );

        history = receiveRewardForConsecutiveDays(
                user,
                LocalDate.of(2022, 12, 2),
                13,
                history
        );

        // 1500 + 700 + 1500 + 3400
        assertThat(user.getPoint()).isEqualTo(7100);
        assertThat(history.getConsecutiveDay()).isEqualTo(new ConsecutiveDay(3));
        assertThat(history.getAmount()).isEqualTo(400);
    }

    @Test
    void testUserReceivesRewardsConsecutive26Days() {
        User user = new User(1, "my_user", 10000);

        RewardHistory history = receiveRewardForConsecutiveDays(
                user,
                LocalDate.of(2022, 10, 3),
                26,
                null
        );

        // 10000 + 2800(10 days) + 2800(10 days) + 1400(6 days)
        assertThat(user.getPoint()).isEqualTo(17000);
        assertThat(history.getConsecutiveDay()).isEqualTo(new ConsecutiveDay(6));
        assertThat(history.getAmount()).isEqualTo(100);
    }

    RewardHistory receiveRewardForConsecutiveDays(User user, LocalDate from, int days, @Nullable RewardHistory rewardHistory) {
        LocalDate date = from;

        if (rewardHistory == null) {
            rewardHistory = user.receiveFirstReward(date);
        } else {
            rewardHistory = user.receiveRewardWithHistory(rewardHistory, date);
        }

        for (int i = 0; i < days - 1; i++) {
            date = date.plusDays(1);

            rewardHistory = user.receiveRewardWithHistory(rewardHistory, date);
        }

        return rewardHistory;
    }
}
