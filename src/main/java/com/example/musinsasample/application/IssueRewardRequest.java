package com.example.musinsasample.application;

import jakarta.validation.constraints.Min;

public record IssueRewardRequest(@Min(1) int userId) {
}
