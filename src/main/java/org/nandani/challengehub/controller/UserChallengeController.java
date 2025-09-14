package org.nandani.challengehub.controller;

import org.nandani.challengehub.dto.UserChallengeResponse;
import org.nandani.challengehub.dto.UserTaskProgressRequest;
import org.nandani.challengehub.service.UserChallengeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserChallengeController {
    private static final Logger logger = LoggerFactory.getLogger(UserChallengeController.class);


    @Autowired
    private UserChallengeService userChallengeService;

    @GetMapping("/{userId}/challenges")
    public ResponseEntity<List<UserChallengeResponse>> getUserChallengesWithStreaks(@PathVariable Long userId) {
        logger.info("Received challenge list request with streaks for userID: {}", userId);
        List<UserChallengeResponse> userChallenges = userChallengeService.getChallengesWithUserStatus(userId);
        return ResponseEntity.ok(userChallenges);
    }



    @PostMapping("/task-progress")
    public ResponseEntity<Map<String, String>> addUserTaskProgress(@RequestBody UserTaskProgressRequest request) {
        logger.info("Inserting task progress for userId={}, challengeId={}, taskId={}, using current date, completed={}",
                request.getUserId(), request.getChallengeId(), request.getTaskId(), request.getCompleted());

        boolean inserted = userChallengeService.insertUserTaskProgress(request);

        Map<String, String> response = new HashMap<>();
        if (inserted) {
            response.put("message", "Task progress recorded.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("message", "Progress already exists for given date.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @GetMapping("/{userId}/challenges/{challengeId}/tasks/status")
    public ResponseEntity<List<Map<String, Object>>> getAllTaskStatusesForToday(
            @PathVariable Long userId,
            @PathVariable Long challengeId) {

        logger.info("Fetching all task statuses for userId={}, challengeId={} for today", userId, challengeId);
        List<Map<String, Object>> taskStatuses = userChallengeService.getAllTaskStatusesForToday(userId, challengeId);
        return ResponseEntity.ok(taskStatuses);
    }

    @PostMapping("/{userId}/activate/{challengeId}")
    public ResponseEntity<String> activateChallenge(
            @PathVariable Long userId,
            @PathVariable Long challengeId) {
        userChallengeService.activateChallengeForUser(userId, challengeId);
        return ResponseEntity.ok("Challenge activated.");
    }



}
