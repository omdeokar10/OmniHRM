package com.example.performance_management.dto.timesheet;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskFetchUserDateRange {
    String username;
    String startDate;
    String endDate;
}
