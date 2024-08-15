package com.example.performance_management.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequestDto {
    private String userName;
    private String password;
    private boolean adminLogin;

}
