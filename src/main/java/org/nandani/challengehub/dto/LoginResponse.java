package org.nandani.challengehub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private boolean success;
    private String message;
    private int userId;
    private String username;
    private String email;
}
