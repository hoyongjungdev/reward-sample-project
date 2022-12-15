package com.example.musinsasample.domain.repository;

import com.example.musinsasample.domain.entity.RewardHistory;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RewardHistoryRepository extends CrudRepository<RewardHistory, Integer> {
    boolean existsByIssuedAtBetweenAndUsername(LocalDateTime from, LocalDateTime to, String username);

    Optional<RewardHistory> getFirstByUsernameOrderByIssuedAtDesc(String username);

    List<RewardHistory> findRewardHistoriesByIssuedAtBetween(
            LocalDateTime from,
            LocalDateTime to,
            Sort sort
    );
}
