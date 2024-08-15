package com.example.performance_management.mapper;

import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.entity.Employee;
import com.example.performance_management.utils.DetailsSetUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EmployeeMapper {

    private final DetailsSetUtils detailsSetUtils = new DetailsSetUtils();
    public EmployeeMapper() {
    }

    public EmployeeDto convertToDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        detailsSetUtils.setIfNotNull(employee::getId, dto::setId);
        detailsSetUtils.setIfNotNull(employee::getUserName, dto::setUserName);
        detailsSetUtils.setIfNotNull(employee::getRoles, dto::setRoles);
        detailsSetUtils.setIfNotNull(employee::getEmail, dto::setEmail);
        detailsSetUtils.setIfNotNull(employee::getCompanyName, dto::setCompanyName);
        detailsSetUtils.setIfNotNull(employee::getPassword, dto::setPassword);
        return dto;
    }

    public Employee convertToEntity(EmployeeDto dto, PasswordEncoder passwordEncoder) {
        Employee entity = new Employee();
        detailsSetUtils.setIfNotNull(dto::getId, entity::setId);
        detailsSetUtils.setIfNotNull(dto::getUserName, entity::setUserName);
        detailsSetUtils.setIfNotNull(dto::getEmail, entity::setEmail);
        detailsSetUtils.setIfNotNull(dto::getPassword, password -> entity.setPassword(passwordEncoder.encode(password)));
        detailsSetUtils.setIfNotNull(dto::getCompanyName, entity::setCompanyName);
        detailsSetUtils.setIfNotNull(dto::getRoles, entity::setRoles);
        return entity;
    }


    public Employee updateEmployee(Employee entity, EmployeeDto dto, PasswordEncoder passwordEncoder) {
        detailsSetUtils.setIfNotNull(dto::getCompanyName, entity::setCompanyName);
        detailsSetUtils.setIfNotNull(dto::getRoles, entity::setRoles);
        detailsSetUtils.setIfNotNull(dto::getPassword, password -> entity.setPassword(passwordEncoder.encode(password)));
        detailsSetUtils.setIfNotNull(dto::getUserName, entity::setUserName);
        detailsSetUtils.setIfNotNull(dto::getEmail, entity::setEmail);
        return entity;
    }
}
