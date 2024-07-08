package com.example.performance_management.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {
    private String email;
    private String username;
    private String password;
    private String dateOfBirth;
    private List<String> roles;
    private String team;
    private Long id;

}
