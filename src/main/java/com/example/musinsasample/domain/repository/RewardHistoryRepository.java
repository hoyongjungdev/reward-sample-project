package com.example.musinsasample.domain.repository;

import com.example.musinsasample.domain.entity.RewardHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RewardHistoryRepository extends CrudRepository<RewardHistory, Integer> {
    @Query("select count(rh) > 0 " +
            "from RewardHistory rh where rh.issuedAt >= :from and rh.issuedAt <= :to " +
            "and rh.username = :username")
    boolean existsByIssuedAtAndUsername(LocalDateTime from, LocalDateTime to, String username);

    Optional<RewardHistory> getFirstByUsernameOrderByIssuedAt(String username);
}
