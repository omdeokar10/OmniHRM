package com.example.performance_management.entity.timesheet;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Attendance {

    @Transient
    public static final String GENERATED_ID = "attendanceId"; //IdSequence.taskId
    @Id
    public Long id;
    public String username;
    public String date;
    public boolean isPresent = true;
    public String punchInTime;
    public String punchOutTime;
}
