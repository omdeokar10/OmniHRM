package com.example.performance_management.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employee_forms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeForms {

    private String employeeName;
    private String formName;
    private Form form;

}
