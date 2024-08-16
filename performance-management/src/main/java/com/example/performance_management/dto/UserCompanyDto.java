package com.example.performance_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCompanyDto {

    public String companyName;
    public String companyDomain;
    public String companyEmail;
    public String userName;
}
