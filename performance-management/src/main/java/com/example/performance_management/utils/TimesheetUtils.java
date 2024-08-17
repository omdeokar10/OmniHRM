package com.example.performance_management.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimesheetUtils {

    public static final int SHIFT_HOURS_PER_DAY = 8;

    public int convertToMinutes(String time) {
        String[] timeParts = time.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);
        int seconds = Integer.parseInt(timeParts[2]);
        return (hours * HelperUtil.MINUTES_PER_HOUR) + minutes + (seconds / HelperUtil.MINUTES_PER_HOUR);
    }

    public long differenceInMinutes(String startTimeStr, String endTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);
        Duration duration = Duration.between(startTime, endTime);
        return duration.toMinutes();
    }

    public static void main(String[] args) {

    }

}
