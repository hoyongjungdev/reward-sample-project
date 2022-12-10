package com.example.musinsasample.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class TimeProvider {
    private final JdbcTemplate jdbcTemplate;

    public Instant now() {
        Clock baseClock = Clock.systemUTC();

        int diffInHours = jdbcTemplate.queryForObject(
                "select diff_in_hours from time_intervals", Integer.class
        );

        Clock clock = Clock.offset(baseClock, Duration.ofHours(diffInHours));

        return clock.instant();
    }
}
