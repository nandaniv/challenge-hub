package org.nandani.challengehub.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private int taskId;
    private String name;
    private String description;
    private boolean activeFlag;
    private Timestamp addedZ;
    private Timestamp modifiedZ;


}

