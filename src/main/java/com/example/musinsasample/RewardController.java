package com.example.musinsasample;

import com.example.musinsasample.application.IssueRewardRequest;
import com.example.musinsasample.application.SuccessResponse;
import com.example.musinsasample.domain.service.RewardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class RewardController {

    private final RewardService rewardService;

    @PostMapping("/reward")
    public SuccessResponse issueReward(@Valid @RequestBody IssueRewardRequest request) {
        rewardService.claimReward();

        return new SuccessResponse(true);
    }
}
