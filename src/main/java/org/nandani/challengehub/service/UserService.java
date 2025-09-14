package org.nandani.challengehub.service;


import org.nandani.challengehub.dto.LoginRequest;
import org.nandani.challengehub.dto.LoginResponse;
import org.nandani.challengehub.dto.SignupRequest;
import org.nandani.challengehub.model.User;
import org.nandani.challengehub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public LoginResponse signup(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new LoginResponse(false, "Email already exists",0,
                    request.getUsername(),
                    request.getEmail());
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return new LoginResponse(false, "Username already exists",0,"Username already exists","Username already exists");
        }

        //String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getPassword());

        userRepository.save(user);
        return new LoginResponse(true, "Signup successful",user.getUserId(),
                user.getUsername(),
                user.getEmail());
    }

    public LoginResponse login(LoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .map(user -> {
                    if (request.getPassword().equals(user.getPasswordHash())) {
                        return new LoginResponse(true, "Login successful",
                                user.getUserId(),
                                user.getUsername(),
                                user.getEmail());
                    } else {
                        return new LoginResponse(false, "Incorrect password",
                                user.getUserId(),
                                user.getUsername(),
                                user.getEmail());
                    }
                })
                .orElse(new LoginResponse(false, "User not found",0,"User not found","User not found"));
    }
}
