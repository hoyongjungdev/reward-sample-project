package com.example.musinsasample;

import com.example.musinsasample.application.IssueRewardRequest;
import com.example.musinsasample.application.SuccessResponse;
import com.example.musinsasample.domain.service.RewardService;
import com.example.musinsasample.infra.TimeProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Log4j2
public class RewardController {

    private final RewardService rewardService;

    private final TimeProvider timeProvider;

    @PostMapping("/reward")
    public SuccessResponse issueReward(@Valid @RequestBody IssueRewardRequest request) {
        int userId = request.userId();

        LocalDate today = timeProvider.getDate();

        rewardService.checkIfUserReceivedReward(userId, today);
        rewardService.createRewardCount(today);
        rewardService.issueReward(userId, today);

        return new SuccessResponse(true);
    }
}
