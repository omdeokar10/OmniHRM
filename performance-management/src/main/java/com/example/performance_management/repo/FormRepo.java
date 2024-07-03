package com.example.performance_management.repo;

import com.example.performance_management.entity.Form;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FormRepo extends MongoRepository<Form, Long> {
}
