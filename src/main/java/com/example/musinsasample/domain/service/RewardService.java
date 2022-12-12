package com.example.musinsasample.domain.service;

import com.example.musinsasample.domain.entity.RewardCount;
import com.example.musinsasample.domain.repository.RewardCountRepository;
import com.example.musinsasample.exception.RewardExhaustedException;
import com.example.musinsasample.infra.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RewardService {
    private final RewardCountRepository rewardCountRepository;

    private final TimeProvider timeProvider;

    private final int MAXIMUM_USER_PER_DAY = 10;

    @Transactional
    public void claimReward() {
        LocalDate today = timeProvider.date();

        RewardCount rewardCount = rewardCountRepository.getRewardCountForUpdate(today).orElseGet(
                () -> {
                    RewardCount rewardCountCreated = new RewardCount(today);
                    rewardCountRepository.save(rewardCountCreated);

                    // pessimistic lock을 위해 다시 조회
                    return rewardCountRepository.getRewardCountForUpdate(today).get();
                }
        );

        if (rewardCount.getRewardClaimed() >= MAXIMUM_USER_PER_DAY) {
            throw new RewardExhaustedException();
        }

        rewardCount.increaseRewardClaimed();
    }
}
