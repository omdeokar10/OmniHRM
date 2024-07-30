package com.example.performance_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeHierarchyDto {

    public String username;
    public String email;
    public String managerUsername;
    public String managerEmail;
}
