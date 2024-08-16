package com.example.performance_management.controller.timesheet;

import com.example.performance_management.controller.HelperUtil;
import com.example.performance_management.dto.timesheet.AttendanceDto;
import com.example.performance_management.dto.timesheet.AttendanceFetchUserDateRange;
import com.example.performance_management.dto.timesheet.AttendanceFetchUserDateRangeById;
import com.example.performance_management.dto.timesheet.EmployeeWorkSummaryDto;
import com.example.performance_management.service.timesheet.AttendanceService;
import com.example.performance_management.utils.TimesheetUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin("*")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final HelperUtil helperUtil;
    private final TimesheetUtils timesheetUtils =new TimesheetUtils();
    public AttendanceController(AttendanceService attendanceService, HelperUtil helperUtil) {
        this.attendanceService = attendanceService;
        this.helperUtil = helperUtil;
    }

    @PostMapping("/")
    public void markAttendance(@RequestBody AttendanceDto attendanceDto) { // user, date
        attendanceDto.setUsername(helperUtil.getLoggedInUser());
        attendanceService.markAttendanceForUser(attendanceDto);
    }

    @PostMapping("/login")
    public void logPunchInTime(@RequestBody AttendanceDto attendanceDto) { // user , date
        attendanceDto.setUsername(helperUtil.getLoggedInUser());
        attendanceService.logPunchInTime(attendanceDto);
    }

    @PostMapping("/logout")
    public void logPunchOutTime(@RequestBody AttendanceDto attendanceDto) {
        attendanceDto.setUsername(helperUtil.getLoggedInUser());
        attendanceService.logPunchOutTime(attendanceDto);
    }

    @PostMapping("/range")
    public EmployeeWorkSummaryDto getAttendanceForTimePeriod(@RequestBody AttendanceFetchUserDateRange dto) {
        dto.setUsername(helperUtil.getLoggedInUser());

        List<AttendanceDto> attendanceList = attendanceService.getAttendanceBetweenTime(dto.getUsername(), dto.getStartDate(), dto.getEndDate());
        int daysPresent = 0;
        long minutesWorked = 0;
        for (AttendanceDto attendanceDto : attendanceList) {
            if (attendanceDto.isPresent()) daysPresent += 1;
            if(attendanceDto.getPunchOutTime()!=null && attendanceDto.getPunchInTime()!=null){
                minutesWorked += timesheetUtils.differenceInMinutes(attendanceDto.getPunchInTime(), attendanceDto.getPunchOutTime());
            };
        }
        return new EmployeeWorkSummaryDto(String.valueOf(daysPresent),String.valueOf( minutesWorked/60), String.valueOf(minutesWorked%60));
    }

    @PostMapping("/admin/range")
    public List<AttendanceDto> getAttendanceByIdForTimePeriod(@RequestBody AttendanceFetchUserDateRangeById dto) { // get attendance list for time period.
        return attendanceService.getAttendanceBetweenTimeForUserId(dto.getId(), dto.getStartDate(), dto.getEndDate());
    }


    //no update/delete.


}
