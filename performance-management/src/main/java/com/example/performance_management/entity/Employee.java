package com.example.performance_management.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "employee")
@Data
@NoArgsConstructor
@Getter
@Setter
public class Employee {

    @Transient
    public static final String ID_VAL = "employee_seq";
    @Transient
    public static final String GENERATED_ID = "employeeId"; //IdSequence.formId
    @Transient
    public static final String ID_KEY = "employeeid"; //IdSequence.formId
    @Id
    private Long id;
    private String fullName;
    private String dateOfBirth;
    private String role;
    private String team;
}
