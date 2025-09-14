package org.nandani.challengehub.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private String username;
    private String email;
    private String passwordHash;
    private LocalDateTime createdZ;
    private LocalDateTime modifiedZ;

}
