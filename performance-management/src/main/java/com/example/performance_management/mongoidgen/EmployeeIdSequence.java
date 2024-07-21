package com.example.performance_management.mongoidgen;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employee_id_sequence")
@Getter
@Setter
public class EmployeeIdSequence {

    @Id
    private String id;
    private Long employeeId;
    private Long roleId;
    private Long goalId;
    private Long taskId;
    private Long attendanceId;
}
