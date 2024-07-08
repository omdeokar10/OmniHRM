package com.example.performance_management.repo;

import com.example.performance_management.entity.Employee;
import com.example.performance_management.entity.Form;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FormRepo extends MongoRepository<Form, Long> {

    Optional<Form> findByFormNameStartsWith(String name);

}
