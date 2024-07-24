package com.example.performance_management.controller.timesheet;

import com.example.performance_management.dto.timesheet.AttendanceDto;
import com.example.performance_management.dto.timesheet.AttendanceFetchUserDateRange;
import com.example.performance_management.service.timesheet.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin("*")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/")
    public void markAttendance(@RequestBody AttendanceDto attendanceDto){ // user, date
        attendanceService.markAttendanceForUser(attendanceDto);
    }

    @PostMapping("/login")
    public void logPunchInTime(@RequestBody AttendanceDto attendanceDto){ // user , date
        attendanceService.logPunchInTime(attendanceDto);
    }

    @PostMapping("/logout")
    public void logPunchOutTime(@RequestBody AttendanceDto attendanceDto){
        attendanceService.logPunchOutTime(attendanceDto);
    }

    @PostMapping("/range")
    public List<AttendanceDto> getAttendanceForTimePeriod(@RequestBody AttendanceFetchUserDateRange dto){ // get attendance list for time period.
        return attendanceService.getAttendanceBetweenTime(dto.getUsername(), dto.getStartDate(), dto.getEndDate());
    }

    //no update/delete.


}
