package com.example.performance_management.dto.timesheet;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskFetchUserDate {
    String username;
    String createdDate;
}
