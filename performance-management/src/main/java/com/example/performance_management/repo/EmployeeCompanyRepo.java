package com.example.performance_management.repo;

import com.example.performance_management.entity.EmployeeCompanyDetails;
import com.example.performance_management.entity.performance.EmployeeForms;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeCompanyRepo extends MongoRepository<EmployeeCompanyDetails, Long> {
    Optional<EmployeeCompanyDetails> findByEmail(String email);
    Optional<EmployeeCompanyDetails> findByUsername(String username);

}
