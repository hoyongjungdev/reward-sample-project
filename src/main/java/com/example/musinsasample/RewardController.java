package com.example.musinsasample;

import com.example.musinsasample.infra.TimeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@Log4j2
public class RewardController {

    private final TimeProvider timeProvider;

    @GetMapping("/")
    public void hello() {
        Instant time = timeProvider.now();

        log.info(time.toString());
    }
}
