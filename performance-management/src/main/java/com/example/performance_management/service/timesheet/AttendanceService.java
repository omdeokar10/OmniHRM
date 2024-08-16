package com.example.performance_management.service.timesheet;

import com.example.performance_management.dto.EmployeeCompanyDetailsDto;
import com.example.performance_management.dto.timesheet.AttendanceDto;
import com.example.performance_management.entity.Employee;
import com.example.performance_management.entity.timesheet.Attendance;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.mapper.AttendanceMapper;
import com.example.performance_management.mongoidgen.EmployeeSequenceGeneratorService;
import com.example.performance_management.repo.AttendanceRepo;
import com.example.performance_management.service.EmployeeCompanyDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    private final AttendanceRepo attendanceRepo;
    private final EmployeeCompanyDetailsService employeeCompanyDetailsService;
    final private EmployeeSequenceGeneratorService employeeSequenceGeneratorService;
    private final AttendanceMapper attendanceMapper = new AttendanceMapper();

    public AttendanceService(AttendanceRepo attendanceRepo, EmployeeCompanyDetailsService authService, EmployeeSequenceGeneratorService employeeSequenceGeneratorService) {
        this.attendanceRepo = attendanceRepo;
        this.employeeCompanyDetailsService = authService;
        this.employeeSequenceGeneratorService = employeeSequenceGeneratorService;
    }

    public Attendance createAttendanceIfNotExist(AttendanceDto attendanceDto) {
        Attendance attendance;
        String date = attendanceDto.getDate().isEmpty() ? LocalDate.now().toString() : attendanceDto.getDate();
        Optional<Attendance> optionalAttendance = attendanceRepo.findByUsernameAndDate(attendanceDto.getUsername(), date);
        if (optionalAttendance.isEmpty()) {
            attendance = attendanceMapper.convertToEntity(attendanceDto);
            attendance.setId(generateAttendanceId());
            attendanceRepo.save(attendance);
        } else {
            attendance = optionalAttendance.get();
        }
        return attendance;
    }

    private Long generateAttendanceId() {
        return employeeSequenceGeneratorService.getAttendanceSequenceNumber
                (Employee.ID_KEY, Employee.ID_VAL, Attendance.GENERATED_ID);
    }

    public Attendance getAttendance(AttendanceDto attendanceDto) {
        Attendance attendance;
        Optional<Attendance> optionalAttendance = attendanceRepo.findByUsernameAndDate(attendanceDto.getUsername(), attendanceDto.getDate());
        if (optionalAttendance.isPresent()) {
            return optionalAttendance.get();
        } else {
            throw new CustomException("Attendance not present");
        }
    }

    public void markAttendanceForUser(AttendanceDto attendanceDto) { // isPresent is true by default.
        createAttendanceIfNotExist(attendanceDto);
    }

    public void logPunchInTime(AttendanceDto attendanceDto) {
        Attendance attendance = createAttendanceIfNotExist(attendanceDto);

        String date = attendance.getDate();
        String today = parseTimeStringToRetrieveDate(LocalDateTime.now());
        if (date.equals(today) || isTestEntry(attendanceDto)) {
            String punchInTime = attendance.getPunchInTime();
            if (punchInTime == null || punchInTime.trim().isEmpty()) {  // earliest punch in time considered.
                attendance.setPunchInTime(LocalDateTime.now().toString());
            }
        } else {
            throw new CustomException("Punch in can be done for the current day only.");
        }
        attendanceRepo.save(attendance);
    }

    private boolean isTestEntry(AttendanceDto attendanceDto) {
        return attendanceDto.getTestEntry().equals("true");
    }

    public void logPunchOutTime(AttendanceDto attendanceDto) {
        Attendance attendance = createAttendanceIfNotExist(attendanceDto);

        LocalDateTime punchInTime = LocalDateTime.parse(attendance.getPunchInTime());
        String today = parseTimeStringToRetrieveDate(LocalDateTime.now());
        String currentPunchInTime = parseTimeStringToRetrieveDate(punchInTime);
        if (today.equals(currentPunchInTime) || isTestEntry(attendanceDto)) {
            attendance.setPunchOutTime(LocalDateTime.now().toString());
        } else {
            throw new CustomException("Punch in date time and punch out date time do not match.");
        }
        attendanceRepo.save(attendance);
    }

    public String parseTimeStringToRetrieveDate(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return time.format(formatter);
    }

    public void deleteAttendanceEntry(AttendanceDto attendanceDto) {
        Attendance attendance = getAttendance(attendanceDto);
        attendanceRepo.delete(attendance);
    }

    public AttendanceDto getAttendanceForDate(String username, String date) {
        Optional<Attendance> attendance = attendanceRepo.findByUsernameAndDate(username, date);
        if (attendance.isPresent()) {
            return attendanceMapper.convertToDto(attendance.get());
        } else {
            return attendanceMapper.emptyDto(username, date);
        }
    }

    public List<AttendanceDto> getAttendanceBetweenTime(String username, String startDate, String endDate) {

        List<AttendanceDto> attendance = new ArrayList<>();
        for (LocalDate date = parse(startDate); date.isBefore(parse(endDate)); date = date.plusDays(1)) {
            attendance.add(getAttendanceForDate(username, date.toString()));
        }
        attendance.add(getAttendanceForDate(username, parse(endDate).toString()));
        return attendance;
    }

    private LocalDate parse(String startDate) {
        return LocalDate.parse(startDate);
    }

    public <T> T getAttendance(Optional<T> optionalAttendance) {
        if (optionalAttendance.isEmpty()) {
            throw new CustomException("Task is not present");
        }
        return optionalAttendance.get();
    }

    public List<AttendanceDto> getAttendanceBetweenTimeForUserId(Long id, String startDate, String endDate) {
        EmployeeCompanyDetailsDto detailsForEmployeeById = employeeCompanyDetailsService.getDetailsForEmployeeById(id);
        String username = detailsForEmployeeById.getUserName();
        return getAttendanceBetweenTime(username, startDate, endDate);
    }
}
