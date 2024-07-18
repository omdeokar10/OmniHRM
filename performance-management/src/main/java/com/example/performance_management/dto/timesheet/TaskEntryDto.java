package com.example.performance_management.dto.timesheet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntryDto {

    private Long id;
    private String description;
    private String username;
    private String durationLogged;
    private String date;

}
