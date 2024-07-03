package com.example.performance_management.mapper;

import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.entity.Employee;

public class EmployeeMapper {

    public EmployeeMapper() {
    }


    public EmployeeDto convertToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFullName(employee.getFullName());
        employeeDto.setDateOfBirth(employee.getDateOfBirth());
        employeeDto.setRole(employee.getRole());
        employeeDto.setTeam(employee.getTeam());
        return employeeDto;
    }

    public Employee convertToEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setFullName(employeeDto.getFullName());
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        employee.setRole(employeeDto.getRole());
        employee.setTeam(employeeDto.getTeam());
        return employee;
    }


}
