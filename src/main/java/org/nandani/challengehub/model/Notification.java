package org.nandani.challengehub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private Long id;
    private Long userId;
    private String message;
    private boolean read;
    private LocalDateTime createdAt;

    // constructor, getters, setters
}
