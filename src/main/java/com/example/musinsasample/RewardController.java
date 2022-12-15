package com.example.musinsasample;

import com.example.musinsasample.application.CreateUserRequest;
import com.example.musinsasample.application.GetRewardHistoriesResponse;
import com.example.musinsasample.application.IssueRewardRequest;
import com.example.musinsasample.application.RewardHistoryDto;
import com.example.musinsasample.application.SuccessResponse;
import com.example.musinsasample.domain.entity.RewardHistory;
import com.example.musinsasample.domain.service.RewardService;
import com.example.musinsasample.domain.service.UserService;
import com.example.musinsasample.exception.ValidationException;
import com.example.musinsasample.infra.TimeProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class RewardController {

    private final RewardService rewardService;
    private final UserService userService;

    private final TimeProvider timeProvider;

    @PostMapping("rewards")
    public SuccessResponse issueReward(@RequestBody IssueRewardRequest request) {
        String username = request.username();

        LocalDateTime now = timeProvider.getDateTime();
        LocalDate today = now.toLocalDate();

        rewardService.checkIfUserReceivedReward(username, today);
        rewardService.createRewardCount(today);
        rewardService.issueReward(username, now);

        return new SuccessResponse(true);
    }

    @GetMapping("rewards/histories")
    public GetRewardHistoriesResponse getRewardHistories(@RequestParam("date") String dateInput, @RequestParam("sort_by") String sortBy) {
        LocalDate date = LocalDate.parse(dateInput);
        Sort.Direction direction;

        if (sortBy.equals("asc")) {
            direction = Sort.Direction.ASC;
        } else if (sortBy.equals("desc")) {
            direction = Sort.Direction.DESC;
        } else {
            throw new ValidationException("올바르지 않은 sort_by 값입니다.");
        }

        List<RewardHistory> rewardHistories = rewardService.getRewardHistories(date, direction);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        List<RewardHistoryDto> rewardHistoryDtos = rewardHistories.stream().map(rh ->
                new RewardHistoryDto(
                        formatter.format(rh.getIssuedAt()),
                        rh.getUsername(),
                        rh.getAmount(),
                        rh.getConsecutiveDay().getDay()
                )
        ).toList();

        return new GetRewardHistoriesResponse(true, rewardHistoryDtos);
    }

    @GetMapping(path = "notifications", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getNotifications() {
        Resource resource = new ClassPathResource("/static/notifications.json");
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(resource.getInputStream(), Object.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("users")
    public SuccessResponse createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request.username());

        return new SuccessResponse(true);
    }
}
