package com.example.performance_management.dto.performance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeLoginResponseDto {
    private String username;
    private String accessToken;
    private String refreshToken;
    private String companyName;
    private String[] roles;
}
