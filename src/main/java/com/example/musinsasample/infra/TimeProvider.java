package com.example.musinsasample.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class TimeProvider {
    private final JdbcTemplate jdbcTemplate;

    public LocalDate getDate() {
        return LocalDate.ofInstant(now(), ZoneId.of("+9"));
    }

    private Instant now() {
        Clock baseClock = Clock.system(ZoneOffset.of("+9"));

        int diffInHours = jdbcTemplate.queryForObject(
                "select diff_in_hours from time_intervals", Integer.class
        );

        Clock clock = Clock.offset(baseClock, Duration.ofHours(diffInHours));

        return clock.instant();
    }
}
