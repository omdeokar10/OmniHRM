package com.example.performance_management.repo;

import com.example.performance_management.entity.Goal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepo extends MongoRepository<Goal, Long> {
}
