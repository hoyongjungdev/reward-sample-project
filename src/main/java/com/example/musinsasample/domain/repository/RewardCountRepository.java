package com.example.musinsasample.domain.repository;

import com.example.musinsasample.domain.entity.RewardCount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RewardCountRepository extends CrudRepository<RewardCount, Integer> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select rc from RewardCount rc where rc.rewardDate = :date")
    Optional<RewardCount> getRewardCountForUpdate(LocalDate date);
}
