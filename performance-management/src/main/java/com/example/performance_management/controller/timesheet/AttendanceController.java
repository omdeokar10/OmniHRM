package com.example.performance_management.controller.timesheet;

import com.example.performance_management.controller.HelperUtil;
import com.example.performance_management.dto.timesheet.AttendanceDto;
import com.example.performance_management.dto.timesheet.AttendanceFetchUserDateRange;
import com.example.performance_management.dto.timesheet.AttendanceFetchUserDateRangeById;
import com.example.performance_management.service.timesheet.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin("*")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final HelperUtil helperUtil;

    public AttendanceController(AttendanceService attendanceService, HelperUtil helperUtil) {
        this.attendanceService = attendanceService;
        this.helperUtil = helperUtil;
    }

    @PostMapping("/")
    public void markAttendance(@RequestBody AttendanceDto attendanceDto){ // user, date
        attendanceDto.setUsername(helperUtil.getLoggedInUser());
        attendanceService.markAttendanceForUser(attendanceDto);
    }

    @PostMapping("/login")
    public void logPunchInTime(@RequestBody AttendanceDto attendanceDto){ // user , date
        attendanceDto.setUsername(helperUtil.getLoggedInUser());
        attendanceService.logPunchInTime(attendanceDto);
    }

    @PostMapping("/logout")
    public void logPunchOutTime(@RequestBody AttendanceDto attendanceDto){
        attendanceDto.setUsername(helperUtil.getLoggedInUser());
        attendanceService.logPunchOutTime(attendanceDto);
    }

    @PostMapping("/range")
    public List<AttendanceDto> getAttendanceForTimePeriod(@RequestBody AttendanceFetchUserDateRange dto){ // get attendance list for time period.
        dto.setUsername(helperUtil.getLoggedInUser());
        return attendanceService.getAttendanceBetweenTime(dto.getUsername(), dto.getStartDate(), dto.getEndDate());
    }

    @PostMapping("/admin/range")
    public List<AttendanceDto> getAttendanceByIdForTimePeriod(@RequestBody AttendanceFetchUserDateRangeById dto){ // get attendance list for time period.
        return attendanceService.getAttendanceBetweenTimeForUserId(dto.getId(), dto.getStartDate(), dto.getEndDate());
    }


    //no update/delete.


}
