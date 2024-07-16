package com.example.performance_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeLoginResponseDto {
    private String username;
    private String jwtToken;
    private String[] roles;
}
