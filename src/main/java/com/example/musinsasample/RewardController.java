package com.example.musinsasample;

import com.example.musinsasample.application.IssueRewardRequest;
import com.example.musinsasample.application.SuccessResponse;
import com.example.musinsasample.domain.service.RewardService;
import com.example.musinsasample.infra.TimeProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Log4j2
public class RewardController {

    private final RewardService rewardService;

    private final TimeProvider timeProvider;

    @PostMapping("/rewards")
    public SuccessResponse issueReward(@RequestBody IssueRewardRequest request) {
        String username = request.username();

        LocalDate today = timeProvider.getDate();

        rewardService.checkIfUserReceivedReward(username, today);
        rewardService.createRewardCount(today);
        rewardService.issueReward(username, today);

        return new SuccessResponse(true);
    }

    @GetMapping(path = "notifications", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getNotifications() {
        Resource resource = new ClassPathResource("/static/notifications.json");
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(resource.getInputStream(), Object.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
