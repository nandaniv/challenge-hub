package org.nandani.challengehub.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Challenge {
    private int challengeId;
    private String name;
    private String description;
    private Boolean activeFlag;
    private LocalDateTime addedZ;
    private LocalDateTime modifiedZ;

}

