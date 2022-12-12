package com.example.musinsasample.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Embeddable
public class ConsecutiveDay {
    @Getter
    @Column(name = "consecutive_day")
    private int day;

    private static final int MAXIMUM_CONSECUTIVE_DAYS = 10;

    protected ConsecutiveDay() {
        day = 1;
    }

    public ConsecutiveDay(int day) {
        this.day = day;
    }

    public ConsecutiveDay nextDay() {
        int nextDay;

        if (day == MAXIMUM_CONSECUTIVE_DAYS) {
            nextDay = 1;
        } else {
            nextDay = day + 1;
        }

        return new ConsecutiveDay(nextDay);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsecutiveDay that = (ConsecutiveDay) o;
        return day == that.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day);
    }
}
