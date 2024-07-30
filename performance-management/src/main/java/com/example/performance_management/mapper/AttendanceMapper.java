package com.example.performance_management.mapper;

import com.example.performance_management.dto.timesheet.AttendanceDto;
import com.example.performance_management.entity.timesheet.Attendance;

public class AttendanceMapper {

    public AttendanceDto convertToDto(Attendance attendance){
        AttendanceDto attendanceDto = new AttendanceDto();
        attendanceDto.setUsername(attendance.getUsername());
        attendanceDto.setDate(attendance.getDate());
        attendanceDto.setPunchInTime(attendance.getPunchInTime());
        attendanceDto.setPunchOutTime(attendance.getPunchOutTime());
        return attendanceDto;
    }

    public AttendanceDto emptyDto(String username, String date){
        AttendanceDto attendanceDto = new AttendanceDto();
        attendanceDto.setUsername(username);
        attendanceDto.setDate(date);
        attendanceDto.setPresent(false);
        return attendanceDto;
    }

    public Attendance convertToEntity(AttendanceDto attendanceDto){
        Attendance attendance = new Attendance();
        attendance.setUsername(attendanceDto.getUsername());
        attendance.setDate(attendanceDto.getDate());
        return attendance;
    }


}
