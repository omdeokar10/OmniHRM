package com.example.performance_management.controller;

import com.example.performance_management.dto.CompanyDto;
import com.example.performance_management.dto.UserCompanyDto;
import com.example.performance_management.entity.Company;
import com.example.performance_management.mapper.CompanyMapper;
import com.example.performance_management.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

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
    public ResponseEntity<String> createCompany(@RequestBody UserCompanyDto companyDto) throws ExecutionException, InterruptedException {

        String userPassword = companyService.sendMail(companyDto.getCompanyEmail()).get();
        companyService.createCompany(companyDto.getCompanyName(), companyDto.getCompanyDomain(),
                companyDto.getCompanyEmail());

        companyService.createCompanyAdmin(companyDto.getUserName(),
                companyDto.getCompanyEmail(), userPassword, companyDto.getCompanyName());

        return new ResponseEntity<>("Company created: " + companyDto.getCompanyName(), HttpStatus.OK);
    }


    @GetMapping("/all")
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
    public ResponseEntity<String> updateCompany(@RequestBody CompanyDto companyDto) {
        CompanyDto updatedDto = companyService.updateCompany(companyDto);
        return new ResponseEntity<>("Updated company: " + updatedDto.getCompanyName(), HttpStatus.OK);
    }

    @DeleteMapping("/name/{companyName}")
    public ResponseEntity<String> delete(@PathVariable String companyName) {
        companyService.deleteCompanyByName(companyName);
        return new ResponseEntity<>("Company deleted.", HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteById(@PathVariable @Valid Long id) {
        companyService.deleteCompanyById(id);
        return new ResponseEntity<>("Company deleted.", HttpStatus.OK);
    }

}
