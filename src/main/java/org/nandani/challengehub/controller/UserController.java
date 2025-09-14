package org.nandani.challengehub.controller;

import org.nandani.challengehub.dto.LoginRequest;
import org.nandani.challengehub.dto.LoginResponse;
import org.nandani.challengehub.dto.SignupRequest;
import org.nandani.challengehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public LoginResponse signup(@RequestBody SignupRequest request) {
        logger.info("📩 Received signup request for username: {}", request.getUsername());
        return userService.signup(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        logger.info("🔐 Received login request for email: {}", request.getEmail());
        return userService.login(request);
    }
}
