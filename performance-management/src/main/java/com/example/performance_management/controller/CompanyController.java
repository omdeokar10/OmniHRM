package com.example.performance_management.controller;

import com.example.performance_management.dto.CompanyDto;
import com.example.performance_management.dto.LoginRequestDto;
import com.example.performance_management.dto.UserCompanyDto;
import com.example.performance_management.dto.performance.EmployeeLoginResponseDto;
import com.example.performance_management.entity.Company;
import com.example.performance_management.mapper.CompanyMapper;
import com.example.performance_management.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/company")
@CrossOrigin("*")
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    public CompanyController(CompanyService companyService, CompanyMapper companyMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    @PostMapping("")
    public ResponseEntity<String> create(@RequestBody UserCompanyDto companyDto) {
        String userPassword = companyService.sendMail(companyDto.getCompanyEmail());
        companyService.createCompany(companyDto.getCompanyName(), companyDto.getCompanyDomain(), companyDto.getCompanyEmail());

        companyService.createCompanyAdmin(companyDto.getUserName(), companyDto.getFirstName(), companyDto.getLastName(),
                companyDto.getCompanyEmail(), userPassword, companyDto.getCompanyName());

        return new ResponseEntity<>("Company created: " + companyDto.getCompanyName(), HttpStatus.OK);
    }


    @GetMapping("/get-all")
    public ResponseEntity<String> getAll() {
        String allCompanies = companyService.getAll();
        return new ResponseEntity<>(allCompanies, HttpStatus.OK);
    }

    @GetMapping("/{companyName}")
    public ResponseEntity<CompanyDto> getByName(@PathVariable String companyName) {
        Company company = companyService.getCompanyByName(companyName);
        CompanyDto companyDto = companyMapper.convertToDto(company);
        return new ResponseEntity<>(companyDto, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<String> update(@RequestBody CompanyDto companyDto) {
        CompanyDto updatedDto = companyService.updateCompany(companyDto);
        return new ResponseEntity<>("Updated company: " + updatedDto.getCompanyName(), HttpStatus.OK);
    }

    @DeleteMapping("/{companyName}")
    public ResponseEntity<String> delete(@PathVariable String companyName) {
        companyService.deleteCompanyByName(companyName);
        return new ResponseEntity<>("Company deleted.", HttpStatus.OK);
    }

}
