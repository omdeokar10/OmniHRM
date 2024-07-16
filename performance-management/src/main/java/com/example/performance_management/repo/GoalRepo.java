package com.example.performance_management.repo;

import com.example.performance_management.entity.Goal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoalRepo extends MongoRepository<Goal, Long> {
    Optional<List<Goal>> findByEmployeeName(String employeeName);
}
