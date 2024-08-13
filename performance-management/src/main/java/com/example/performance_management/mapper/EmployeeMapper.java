package com.example.performance_management.mapper;

import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.entity.Employee;
import com.example.performance_management.entity.role.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

public class EmployeeMapper {

    public EmployeeMapper() {
    }

    public EmployeeDto convertToDto(Employee employee, PasswordEncoder passwordEncoder) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setUserName(employee.getUserName());
        dto.setRoles(employee.getRoles());
        dto.setEmail(employee.getEmail());
        dto.setCompanyName(employee.getCompanyName());
        dto.setPassword(employee.getPassword());
        return dto;
    }

    public Employee convertToEntity(EmployeeDto dto, PasswordEncoder passwordEncoder) {
        Employee entity = new Employee();
        entity.setId(dto.getId());
        entity.setUserName(dto.getUserName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setCompanyName(dto.getCompanyName());
        entity.setRoles(dto.getRoles());
        setRoles(dto, entity);
        return entity;
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


    public Employee updateEmployee(Employee entity, EmployeeDto dto, PasswordEncoder passwordEncoder) {
        entity.setCompanyName(dto.getCompanyName());
        entity.setRoles(dto.getRoles());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setUserName(dto.getUserName());
        entity.setEmail(dto.getEmail());
        return entity;
    }
}
