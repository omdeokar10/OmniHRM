package com.deokarkaustubh.database.dao;

import com.deokarkaustubh.database.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAttendanceDao {
    public String day;
    private Integer userid;
    private boolean present;
    @Id
    @GeneratedValue
    private Long userAttendanceId;

    public String startTime;
    public String endTime;

}
