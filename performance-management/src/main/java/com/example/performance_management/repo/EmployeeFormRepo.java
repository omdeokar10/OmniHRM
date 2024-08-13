package com.example.performance_management.repo;

import com.example.performance_management.entity.performance.EmployeeForms;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmployeeFormRepo extends MongoRepository<EmployeeForms, Long> {

    Optional<EmployeeForms> findByCompanyNameAndEmployeeNameAndFormName(String companyName, String employeeName, String formName);

    Optional<EmployeeForms> findByCompanyNameAndEmployeeName(String companyName, String employeeName);

}
