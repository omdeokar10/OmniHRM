package com.example.performance_management.service;

import com.example.performance_management.dto.CompanyDto;
import com.example.performance_management.dto.EmployeeCompanyDetailsDto;
import com.example.performance_management.dto.performance.EmployeeLoginResponseDto;
import com.example.performance_management.entity.Company;
import com.example.performance_management.entity.role.Role;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.mapper.CompanyMapper;
import com.example.performance_management.mongoidgen.CompanySequenceGeneratorService;
import com.example.performance_management.repo.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

@Service
public class CompanyService {

    private final CompanyRepo companyRepo;
    private final CompanyMapper companyMapper;
    private final CompanySequenceGeneratorService companySequenceGeneratorService;
    private final RoleService roleService;
    private final AuthService authService;
    private final EmployeeCompanyDetailsService employeeCompanyDetailsService;
    @Autowired
    private JavaMailSender javaMailSender;

    public CompanyService(CompanyRepo companyRepo, CompanyMapper companyMapper, CompanySequenceGeneratorService companySequenceGeneratorService, RoleService roleService, AuthService authService, EmployeeCompanyDetailsService employeeCompanyDetailsService) {
        this.companyRepo = companyRepo;
        this.companyMapper = companyMapper;
        this.companySequenceGeneratorService = companySequenceGeneratorService;
        this.roleService = roleService;
        this.authService = authService;
        this.employeeCompanyDetailsService = employeeCompanyDetailsService;
    }

    public void createCompany(String companyName, String companyDomain, String companyEmail) {
        checkIfExists(companyName);
        long id = companySequenceGeneratorService.getCompanySequenceNumber(Company.ID_KEY, Company.ID_VAL, Company.GENERATED_ID);
        Company company = new Company(id, companyName, companyDomain, companyEmail);
        companyRepo.save(company);
    }

    public CompanyDto updateCompany(CompanyDto companyDto) {
        checkIfExists(companyDto.getCompanyName());
        Company company = companyMapper.convertToEntity(companyDto);
        companyRepo.save(company);
        return companyDto;
    }

    private void checkIfExists(String companyName) {
        if (checkForSameName(companyName)) {
            throw new CustomException("Company already registered.");
        }
    }

    public boolean checkForSameName(String name) {
        return companyRepo.findByCompanyNameStartsWith(name).isPresent();
    }

    public void deleteCompanyById(Long id) {
        companyRepo.deleteById(id);
    }

    public Company getCompanyById(Long id) {
        Optional<Company> optionalCom = companyRepo.findById(id);
        if (optionalCom.isEmpty()) {
            throw new CustomException("Company does not exist");
        }
        return optionalCom.get();
    }

    public String getAll() {
        List<Company> all = companyRepo.findAll();
        StringJoiner stringJoiner = new StringJoiner(", ", "Companies{", "}");
        all.stream().forEach(company -> stringJoiner.add(company.getCompanyName()));
        return stringJoiner.toString();
    }

    public Company getCompanyByName(String companyName) {
        Optional<Company> optionalCompany = companyRepo.findByCompanyNameStartsWith(companyName);
        if (optionalCompany.isEmpty()) {
            throw new CustomException("Company is not present");
        }
        return optionalCompany.get();
    }

    public void deleteCompanyByName(String companyName) {
        companyRepo.deleteByCompanyName(companyName);
    }

    @Async
    public String sendMail(String companyEmail) {

        int index = companyEmail.indexOf('@');
        String password = companyEmail.substring(0, index);

        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setFrom("kaustubhdeokarsde@gmail.com");
        simpleMessage.setTo(companyEmail);
        simpleMessage.setSubject("Use this password to login into the platform.");
        simpleMessage.setText("Password:" + password);
        javaMailSender.send(simpleMessage);

        return password;
    }

    public EmployeeLoginResponseDto companyAdminLogin(String username, String password) {
        return authService.employeeLogin(username, password);
    }

    private Company getCompany(Optional<Company> optionalCompany) {
        if (optionalCompany.isEmpty()) {
            throw new CustomException("Company does not exist");
        }
        return optionalCompany.get();
    }

    public void createCompanyAdmin(String userName, String firstName, String lastName, String companyEmail, String userPassword, String companyName) {
        StringJoiner fullName = new StringJoiner(" ");
        fullName.add(firstName);
        fullName.add(lastName);

        Role officeAdmin = roleService.getRole("office_admin");
        EmployeeCompanyDetailsDto employeeCompanyDetailsDto = new EmployeeCompanyDetailsDto();

        employeeCompanyDetailsDto.setCompanyName(companyName);
        employeeCompanyDetailsDto.setFullName(fullName.toString());
        employeeCompanyDetailsDto.setEmail(companyEmail);
        employeeCompanyDetailsDto.setUserName(userName);
        employeeCompanyDetailsDto.setPassword(userPassword);
        employeeCompanyDetailsDto.setRoles(List.of(officeAdmin));
        employeeCompanyDetailsService.createEmployeeForCompany(employeeCompanyDetailsDto);
    }
}
