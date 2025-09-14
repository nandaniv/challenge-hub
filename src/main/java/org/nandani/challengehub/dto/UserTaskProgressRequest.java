package org.nandani.challengehub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTaskProgressRequest {
    private Long userId;
    private Long challengeId;
    private Long taskId;
    private Boolean completed;

}
