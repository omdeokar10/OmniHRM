package com.example.performance_management.dto.timesheet;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeWorkSummaryDto {
    String daysPresent;
    String hoursRecorded;
    String minutesRecorded;
}
