package com.example.performance_management.service;

import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.entity.Employee;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.mapper.EmployeeMapper;
import com.example.performance_management.mongoidgen.EmployeeSequenceGeneratorService;
import com.example.performance_management.repo.EmployeeRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final CompanyService companyService;
    private final EmployeeMapper employeeMapper = new EmployeeMapper();
    private final EmployeeSequenceGeneratorService employeeSequenceGeneratorService;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepo employeeRepo, CompanyService companyService,
                           EmployeeSequenceGeneratorService employeeSequenceGeneratorService, PasswordEncoder passwordEncoder) {
        this.employeeRepo = employeeRepo;
        this.companyService = companyService;
        this.employeeSequenceGeneratorService = employeeSequenceGeneratorService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<EmployeeDto> getAllEmployees() {
        return employeeRepo.findAll().stream().map(employee -> employeeMapper.convertToDto(employee, passwordEncoder))
                .collect(Collectors.toList());
    }

    public Optional<EmployeeDto> getEmployeeById(Long id) {
        return employeeRepo.findById(id).map(employee -> employeeMapper.convertToDto(employee, passwordEncoder));
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto, PasswordEncoder passwordEncoder) {
        performChecks(employeeDto);
        Employee employee = employeeMapper.convertToEntity(employeeDto, passwordEncoder);
        setIds(employee);
        Employee savedEmployee = employeeRepo.save(employee);
        return employeeMapper.convertToDto(savedEmployee, passwordEncoder);
    }

    private void performChecks(EmployeeDto employeeDto) {
        if (checkForSameName(employeeDto.getUserName()) || checkForEmail(employeeDto.getEmail())) {
            throw new CustomException("Name already exists");
        }
        if (!companyService.checkForSameName(employeeDto.getCompanyName())) {
            throw new CustomException("Company does not exist");
        }
    }

    private boolean checkForSameName(String nameStr) {
        return employeeRepo.findByUserNameStartsWith(nameStr).isPresent();
    }

    private boolean checkForEmail(String email) {
        return employeeRepo.findByEmailStartsWith(email).isPresent();
    }

    private void setIds(Employee employee) {
        employee.setId(employeeSequenceGeneratorService.getEmployeeSequenceNumber
                (Employee.ID_KEY, Employee.ID_VAL, Employee.GENERATED_ID));
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDetails) {
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        setDetails(employeeDetails, employee);
        Employee updatedEmployee = employeeRepo.save(employee);
        return employeeMapper.convertToDto(updatedEmployee, passwordEncoder);
    }

    private void setDetails(EmployeeDto employeeDto, Employee employee) {
        employee.setCompanyName(employeeDto.getCompanyName());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setUserName(employeeDto.getUserName());

        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        employee.setRoles(employeeDto.getRoles());
        employee.setTeam(employeeDto.getTeam());
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        employeeRepo.delete(employee);
    }
}