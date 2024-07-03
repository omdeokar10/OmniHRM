package com.example.performance_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private String fullName;
    private String dateOfBirth;
    private String role;
    private String team;
    private Long id;

}
