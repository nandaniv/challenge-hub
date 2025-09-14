package org.nandani.challengehub.controller;

import org.nandani.challengehub.model.Challenge;
import org.nandani.challengehub.model.Task;
import org.nandani.challengehub.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
@CrossOrigin(origins = "*")
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @GetMapping
    public List<Challenge> getAllChallenges() {
        return challengeService.getAllChallenges();
    }
    // New endpoint to get all tasks for a specific challenge
    @GetMapping("/{challengeId}/tasks")
    public List<Task> getTasksForChallenge(@PathVariable int challengeId) {
        return challengeService.getTasksForChallenge(challengeId);
    }
}
