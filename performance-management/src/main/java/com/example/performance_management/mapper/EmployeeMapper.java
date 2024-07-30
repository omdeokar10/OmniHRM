package com.example.performance_management.mapper;

import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.entity.Employee;
import com.example.performance_management.entity.Role;
import org.apache.catalina.util.StringUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

public class EmployeeMapper {

    public EmployeeMapper() {
    }


    public EmployeeDto convertToDto(Employee employee, PasswordEncoder passwordEncoder) {

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setCompanyName(employee.getCompanyName());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setFullName(employee.getFirstName() + " " + employee.getLastName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setUserName(employee.getUserName());
        employeeDto.setDateOfBirth(employee.getDateOfBirth());
        employeeDto.setRoles(employee.getRoles());
        return employeeDto;
    }

    public Employee convertToEntity(EmployeeDto employeeDto, PasswordEncoder passwordEncoder) {
        Employee employee = new Employee();

        employee.setId(employeeDto.getId());
        employee.setCompanyName(employeeDto.getCompanyName());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setFullName(employeeDto.getFirstName() + " " + employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setUserName(employeeDto.getUserName());
        employee.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        setRoles(employeeDto, employee);
        return employee;
    }

    private void setRoles(EmployeeDto employeeDto, Employee employee) {
        List<Role> roles;
        if (employee.getRoles() == null || employee.getRoles().isEmpty()) {
            roles = Collections.emptyList();
        } else {
            roles = employeeDto.getRoles();
        }
        employee.setRoles(roles);
    }


}
