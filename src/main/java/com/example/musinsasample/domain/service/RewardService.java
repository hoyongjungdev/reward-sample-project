package com.example.musinsasample.domain.service;

import com.example.musinsasample.domain.entity.RewardCount;
import com.example.musinsasample.domain.entity.RewardHistory;
import com.example.musinsasample.domain.entity.User;
import com.example.musinsasample.domain.repository.RewardCountRepository;
import com.example.musinsasample.domain.repository.RewardHistoryRepository;
import com.example.musinsasample.domain.repository.UserRepository;
import com.example.musinsasample.exception.DuplicateRewardException;
import com.example.musinsasample.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RewardService {
    private final RewardCountRepository rewardCountRepository;
    private final RewardHistoryRepository rewardHistoryRepository;
    private final UserRepository userRepository;

    public void checkIfUserReceivedReward(String username, LocalDate today) {
        if (rewardHistoryRepository.existsByIssuedAtBetweenAndUsername(
                today.atStartOfDay(), today.atTime(LocalTime.MAX), username
        )) {
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
    public void issueReward(String username, LocalDateTime now) {
        User user = getUserOrThrow(username);
        LocalDate today = now.toLocalDate();

        claimReward(today);
        createRewardHistory(user, now);
    }

    public List<RewardHistory> getRewardHistories(LocalDate date, Sort.Direction direction) {
        return rewardHistoryRepository.findRewardHistoriesByIssuedAtBetween(
                date.atStartOfDay(),
                date.atTime(LocalTime.MAX),
                Sort.by(direction, "issuedAt")
        );
    }

    private void claimReward(LocalDate today) {
        // rewardCount가 항상 존재함
        RewardCount rewardCount = rewardCountRepository.getRewardCountForUpdate(today).get();
        rewardCount.increaseRewardClaimed();
    }

    private void createRewardHistory(User user, LocalDateTime now) {
        Optional<RewardHistory> recentRewardHistoryOptional
                = rewardHistoryRepository.getFirstByUsernameOrderByIssuedAtDesc(user.getUsername());

        RewardHistory rewardHistoryCreated;

        if (recentRewardHistoryOptional.isPresent()) {
            rewardHistoryCreated = user.receiveRewardWithHistory(recentRewardHistoryOptional.get(), now);
        } else {
            rewardHistoryCreated = user.receiveFirstReward(now);
        }

        rewardHistoryRepository.save(rewardHistoryCreated);
    }

    private User getUserOrThrow(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
    }
}
