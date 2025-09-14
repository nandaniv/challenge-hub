package org.nandani.challengehub.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserChallengeResponse {
    private Long challengeId;
    private String title;
    private int currentStreak;
    private Date lastCompletedDate;
    private String status;

}

