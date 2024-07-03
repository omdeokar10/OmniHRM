package com.example.performance_management.service;

import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.entity.Employee;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.mapper.EmployeeMapper;
import com.example.performance_management.mongoidgen.EmployeeSequenceGeneratorService;
import com.example.performance_management.repo.EmployeeRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final EmployeeMapper employeeMapper = new EmployeeMapper();
    private final EmployeeSequenceGeneratorService employeeSequenceGeneratorService;

    public EmployeeService(EmployeeRepo employeeRepo, EmployeeSequenceGeneratorService employeeSequenceGeneratorService) {
        this.employeeRepo = employeeRepo;
        this.employeeSequenceGeneratorService = employeeSequenceGeneratorService;
    }

    public List<EmployeeDto> getAllEmployees() {
        return employeeRepo.findAll().stream().map(employeeMapper::convertToDto).collect(Collectors.toList());
    }

    public Optional<EmployeeDto> getEmployeeById(Long id) {
        return employeeRepo.findById(id).map(employeeMapper::convertToDto);
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        if (checkForSameName(employeeDto.getFullName())) {
            throw new CustomException("Name already exists");
        }
        Employee employee = employeeMapper.convertToEntity(employeeDto);
        setId(employee);
        Employee savedEmployee = employeeRepo.save(employee);
        return employeeMapper.convertToDto(savedEmployee);
    }

    private boolean checkForSameName(String nameStr) {
        return employeeRepo.findByFullNameStartsWith(nameStr).isPresent();
    }

    private void setId(Employee employee) {
        employee.setId(employeeSequenceGeneratorService.getEmployeeSequenceNumber
                (Employee.ID_KEY, Employee.ID_VAL, Employee.GENERATED_ID));
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDetails) {
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        setDetails(employeeDetails, employee);
        Employee updatedEmployee = employeeRepo.save(employee);
        return employeeMapper.convertToDto(updatedEmployee);
    }

    private void setDetails(EmployeeDto employeeDetails, Employee employee) {
        employee.setFullName(employeeDetails.getFullName());
        employee.setDateOfBirth(employeeDetails.getDateOfBirth());
        employee.setRole(employeeDetails.getRole());
        employee.setTeam(employeeDetails.getTeam());
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        employeeRepo.delete(employee);
    }
}