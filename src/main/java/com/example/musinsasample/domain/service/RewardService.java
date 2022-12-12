package com.example.musinsasample.domain.service;

import com.example.musinsasample.domain.entity.RewardCount;
import com.example.musinsasample.domain.entity.RewardHistory;
import com.example.musinsasample.domain.entity.User;
import com.example.musinsasample.domain.repository.RewardCountRepository;
import com.example.musinsasample.domain.repository.RewardHistoryRepository;
import com.example.musinsasample.exception.DuplicateRewardException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RewardService {
    private final RewardCountRepository rewardCountRepository;
    private final RewardHistoryRepository rewardHistoryRepository;

    public void checkIfUserReceivedReward(int userId, LocalDate today) {
        if (rewardHistoryRepository.existsByRewardDateAndUserId(today, userId)) {
            throw new DuplicateRewardException();
        }
    }

    public void createRewardCount(LocalDate today) {
        if (rewardCountRepository.existsByRewardDate(today)) {
            return;
        }

        try {
            RewardCount rewardCountCreated = new RewardCount(today);
            rewardCountRepository.save(rewardCountCreated);
        } catch (DataIntegrityViolationException e) {
            // ignore
        }
    }

    @Transactional
    public void issueReward(int userId, LocalDate today) {
        claimReward(today);
        createRewardHistory(userId, today);
    }

    private void claimReward(LocalDate today) {
        // rewardCount가 항상 존재함
        RewardCount rewardCount = rewardCountRepository.getRewardCountForUpdate(today).get();
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
