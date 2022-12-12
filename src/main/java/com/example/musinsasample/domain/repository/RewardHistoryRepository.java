package com.example.musinsasample.domain.repository;

import com.example.musinsasample.domain.entity.RewardHistory;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RewardHistoryRepository extends CrudRepository<RewardHistory, Integer> {
    boolean existsByRewardDateAndUserId(LocalDate rewardDate, int userId);

    Optional<RewardHistory> getFirstByUserIdOrderByRewardDateDesc(int userId);
}
