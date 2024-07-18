package com.example.performance_management.entity.timesheet;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "task")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {

    @Transient
    public static final String GENERATED_ID = "taskId"; //IdSequence.taskId
    @Id
    private Long id;
    private String description;
    private String username;
    private String durationLogged;
    private String date;

}
