package com.example.performance_management.repo;

import com.example.performance_management.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository  extends JpaRepository<Goal, Long> {
}
