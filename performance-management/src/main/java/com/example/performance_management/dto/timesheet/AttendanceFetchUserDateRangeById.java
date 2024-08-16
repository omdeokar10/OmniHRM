package com.example.performance_management.dto.timesheet;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceFetchUserDateRangeById {

    Long id;
    String startDate;
    String endDate;


}