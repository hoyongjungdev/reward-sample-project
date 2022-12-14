package com.example.musinsasample.domain.repository;

import com.example.musinsasample.domain.entity.RewardHistory;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RewardHistoryRepository extends CrudRepository<RewardHistory, Integer> {
    boolean existsByRewardDateAndUsername(LocalDate rewardDate, String username);

    Optional<RewardHistory> getFirstByUsernameOrderByRewardDateDesc(String username);
}
