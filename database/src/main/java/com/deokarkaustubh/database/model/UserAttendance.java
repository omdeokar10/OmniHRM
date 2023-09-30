package com.deokarkaustubh.database.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class UserAttendance {

    public LocalDate day;
    private Integer userid;
    private boolean present;
    @Id
    @GeneratedValue
    private Long userAttendanceId;

    public Instant startTime;
    public Instant endTime;

    public UserAttendance(LocalDate day, Integer userid, boolean present, Instant startTime, Instant endTime) {
        this.day = day;
        this.userid = userid;
        this.present = present;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
