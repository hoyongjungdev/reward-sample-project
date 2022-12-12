package com.example.musinsasample.domain.service;

import com.example.musinsasample.domain.entity.RewardCount;
import com.example.musinsasample.domain.entity.RewardHistory;
import com.example.musinsasample.domain.entity.User;
import com.example.musinsasample.domain.repository.RewardCountRepository;
import com.example.musinsasample.domain.repository.RewardHistoryRepository;
import com.example.musinsasample.exception.DuplicateRewardException;
import com.example.musinsasample.infra.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RewardService {
    private final RewardCountRepository rewardCountRepository;
    private final RewardHistoryRepository rewardHistoryRepository;

    private final TimeProvider timeProvider;

    public void checkIfUserReceivedReward(int userId) {
        LocalDate today = timeProvider.date();

        if (rewardHistoryRepository.existsByRewardDateAndUserId(today, userId)) {
            throw new DuplicateRewardException();
        }
    }

    @Transactional
    public void issueReward(int userId) {
        LocalDate today = timeProvider.date();

        claimReward(today);
        createRewardHistory(userId, today);
    }

    private void claimReward(LocalDate today) {
        RewardCount rewardCount = rewardCountRepository.getRewardCountForUpdate(today).orElseGet(
                () -> {
                    RewardCount rewardCountCreated = new RewardCount(today);
                    rewardCountRepository.save(rewardCountCreated);

                    // pessimistic lock을 위해 다시 조회
                    return rewardCountRepository.getRewardCountForUpdate(today).get();
                }
        );

        rewardCount.increaseRewardClaimed();
    }

    private void createRewardHistory(int userId, LocalDate today) {
        User user = new User(userId, "", 0);

        Optional<RewardHistory> recentRewardHistoryOptional
                = rewardHistoryRepository.getFirstByUserIdOrderByRewardDateDesc(userId);

        RewardHistory rewardHistoryCreated;

        if (recentRewardHistoryOptional.isPresent()) {
            rewardHistoryCreated = user.receiveRewardWithHistory(recentRewardHistoryOptional.get(), today);
        } else {
            rewardHistoryCreated = user.receiveFirstReward(today);
        }

        rewardHistoryRepository.save(rewardHistoryCreated);
    }
}
