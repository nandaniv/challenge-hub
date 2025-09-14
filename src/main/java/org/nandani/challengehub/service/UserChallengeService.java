package org.nandani.challengehub.service;

import org.nandani.challengehub.dto.UserChallengeResponse;
import org.nandani.challengehub.dto.UserTaskProgressRequest;
import org.nandani.challengehub.model.Challenge;
import org.nandani.challengehub.repository.UserChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserChallengeService {

    @Autowired
    private UserChallengeRepository userChallengeRepository;

    public List<Challenge> getChallengesForUser(Long userId) {
        return userChallengeRepository.findChallengesByUserId(userId);
    }
    public boolean insertUserTaskProgress(UserTaskProgressRequest request) {
        return userChallengeRepository.insertUserTaskProgress(request);
    }
    public List<UserChallengeResponse> getUserChallengesWithStreaks(Long userId) {
        return userChallengeRepository.getUserChallengesWithStreaks(userId);
    }
    public List<Map<String, Object>> getAllTaskStatusesForToday(Long userId, Long challengeId) {
        return userChallengeRepository.getAllTaskStatusesForToday(userId, challengeId);
    }
    public void activateChallengeForUser(Long userId, Long challengeId) {
        userChallengeRepository.activateChallengeForUser(userId,challengeId);
    }
    public List<UserChallengeResponse> getChallengesWithUserStatus(Long userId) {
        return userChallengeRepository.getAllChallengesWithUserStatus(userId);
    }




}
