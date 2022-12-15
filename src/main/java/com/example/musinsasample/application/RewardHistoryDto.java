package com.example.musinsasample.application;

public record RewardHistoryDto(String issuedAt, String username, int amount, int consecutiveDay) {
}
