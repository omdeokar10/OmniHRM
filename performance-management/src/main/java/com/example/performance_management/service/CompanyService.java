package com.example.performance_management.service;

import com.example.performance_management.dto.CompanyDto;
import com.example.performance_management.entity.Company;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.mapper.CompanyMapper;
import com.example.performance_management.mongoidgen.CompanySequenceGeneratorService;
import com.example.performance_management.repo.CompanyRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

@Service
public class CompanyService {

    private final CompanyRepo companyRepo;
    private final CompanyMapper companyMapper;
    private final CompanySequenceGeneratorService companySequenceGeneratorService;

    public CompanyService(CompanyRepo companyRepo, CompanyMapper companyMapper, CompanySequenceGeneratorService companySequenceGeneratorService) {
        this.companyRepo = companyRepo;
        this.companyMapper = companyMapper;
        this.companySequenceGeneratorService = companySequenceGeneratorService;
    }

    public void createCompany(CompanyDto companyDto)
    {
        checkIfExists(companyDto);
        long id = companySequenceGeneratorService.getCompanySequenceNumber(Company.ID_KEY, Company.ID_VAL, Company.GENERATED_ID);
        Company company = new Company(id, companyDto.getCompanyName(), companyDto.getCompanyDomain());
        companyRepo.save(company);
    }

    public CompanyDto updateCompany(CompanyDto companyDto)
    {
        checkIfExists(companyDto);
        Company company = companyMapper.convertToEntity(companyDto);
        companyRepo.save(company);
        return companyDto;
    }

    private void checkIfExists(CompanyDto companyDto) {
        if (checkForSameName(companyDto.getCompanyDomain())) {
            throw new CustomException("Domain should not be same.");
        }
    }

    public boolean checkForSameName(String name){
        return companyRepo.findByCompanyNameStartsWith(name).isPresent();
    }

    public void deleteCompanyById(Long id){
        companyRepo.deleteById(id);
    }

    public Company getCompanyById(Long id) {
        Optional<Company> optionalCom = companyRepo.findById(id);
        if(optionalCom.isEmpty()){
            throw new CustomException("Company does not exist");
        }
        return optionalCom.get();
    }

    public String getAll() {
        List<Company> all = companyRepo.findAll();
        StringJoiner stringJoiner = new StringJoiner(", ","Companies{","}");
        all.stream().forEach(company -> stringJoiner.add(company.getCompanyName()));
        return stringJoiner.toString();
    }

    public Company getCompanyByName(String companyName) {
        Optional<Company> optionalCompany = companyRepo.findByCompanyNameStartsWith(companyName);
        if(!optionalCompany.isPresent()){
            throw new CustomException("Company is not present");
        }
        return optionalCompany.get();
    }

    public void deleteCompanyByName(String companyName) {
        companyRepo.deleteByCompanyName(companyName);
    }
}
