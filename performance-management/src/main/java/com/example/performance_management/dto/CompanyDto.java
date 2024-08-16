package com.example.performance_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    private Long id;
    public String companyName;
    public String companyDomain;
    public String companyEmail;
}
