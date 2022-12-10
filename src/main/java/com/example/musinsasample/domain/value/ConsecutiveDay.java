package com.example.musinsasample.domain.value;

public record ConsecutiveDay(int day) {
    private static final int MAXIMUM_CONSECUTIVE_DAYS = 10;

    public ConsecutiveDay nextDay() {
        int nextDay;

        if (day == MAXIMUM_CONSECUTIVE_DAYS) {
            nextDay = 1;
        } else {
            nextDay = day + 1;
        }

        return new ConsecutiveDay(nextDay);
    }
}
