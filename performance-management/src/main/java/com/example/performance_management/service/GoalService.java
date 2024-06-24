package com.example.performance_management.service;

import com.example.performance_management.entity.Goal;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.repo.GoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalService {

    private final GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public Goal createGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    public Optional<Goal> getGoalById(Long id) {
        return goalRepository.findById(id);
    }

    public Goal updateGoal(Long id, Goal goalDetails) {
        Optional<Goal> goalOptional = goalRepository.findById(id);
        if (goalOptional.isPresent()) {
            Goal goal = goalOptional.get();
            goal.setUsername(goalDetails.getUsername());
            goal.setGoal(goalDetails.getGoal());
            goal.setEmpInput(goalDetails.getEmpInput());
            goal.setMgrFeedback(goalDetails.getMgrFeedback());
            return goalRepository.save(goal);
        }
        throw new CustomException("goal not found.");
    }

    public void deleteGoal(Long id) {
        goalRepository.deleteById(id);
    }

    public void saveGoal(Goal goal) {
        goalRepository.save(goal);
    }
}
