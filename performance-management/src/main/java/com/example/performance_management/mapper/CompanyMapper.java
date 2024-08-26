package com.example.performance_management.mapper;

import com.example.performance_management.dto.CompanyDto;
import com.example.performance_management.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public CompanyDto convertToDto(Company company) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setCompanyDomain(company.getCompanyDomain());
        companyDto.setCompanyName(company.getCompanyName());
        return companyDto;
    }

    public Company convertToEntity(CompanyDto companyDto) {
        Company company = new Company();
        company.setCompanyDomain(companyDto.getCompanyDomain());
        company.setCompanyName(companyDto.getCompanyName());
        return company;
    }
}
