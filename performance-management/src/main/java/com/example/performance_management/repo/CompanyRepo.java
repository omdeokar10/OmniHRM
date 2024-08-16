package com.example.performance_management.repo;

import com.example.performance_management.entity.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepo extends MongoRepository<Company, Long> {

    Optional<Company> findByCompanyNameStartsWith(String name);
    void deleteByCompanyName(String companyName);
}
