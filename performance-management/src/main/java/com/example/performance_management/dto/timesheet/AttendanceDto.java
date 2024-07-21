package com.example.performance_management.dto.timesheet;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AttendanceDto {

    public String username;
    public String date;
    public boolean isPresent = true;
    public String punchInTime;
    public String punchOutTime;
    public String testEntry = "false";

}
