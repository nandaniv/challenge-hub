package org.nandani.challengehub.service;

import org.nandani.challengehub.model.Challenge;
import org.nandani.challengehub.model.Task;
import org.nandani.challengehub.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    public List<Challenge> getAllChallenges() {
        return challengeRepository.getAllChallenges();
    }
    public List<Task> getTasksForChallenge(int challengeId) {
        return challengeRepository.getTasksForChallenge(challengeId);
    }
}
