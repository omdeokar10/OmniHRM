package com.example.performance_management.repo;

import com.example.performance_management.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmployeeRepo extends MongoRepository<Employee, Long> {

    Optional<Employee> findByUserNameStartsWith(String name);
    Optional<Employee> findByEmailStartsWith(String name);
}