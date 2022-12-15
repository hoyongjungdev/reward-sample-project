package com.example.musinsasample.application;

import java.util.List;

public record GetRewardHistoriesResponse(boolean success, List<RewardHistoryDto> histories) {
}
