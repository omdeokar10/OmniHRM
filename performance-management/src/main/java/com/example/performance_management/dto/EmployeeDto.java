package com.example.performance_management.dto;

import com.example.performance_management.entity.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    private String userName;
    private String password;
    private String email;
    private List<Role> roles;
    private String companyName;
}
