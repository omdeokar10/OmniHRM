package com.example.performance_management.service;

import com.example.performance_management.entity.Goal;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.repo.GoalRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalService {

    private final GoalRepo goalRepo;

    public GoalService(GoalRepo goalRepo) {
        this.goalRepo = goalRepo;
    }

    public Goal createGoal(Goal goal) {
        return goalRepo.save(goal);
    }

    public List<Goal> getAllGoals() {
        return goalRepo.findAll();
    }

    public Optional<Goal> getGoalById(Long id) {
        return goalRepo.findById(id);
    }

    public Goal updateGoal(Long id, Goal goalDetails) {
        Optional<Goal> goalOptional = goalRepo.findById(id);
        if (goalOptional.isPresent()) {
            Goal goal = goalOptional.get();
            goal.setUsername(goalDetails.getUsername());
            goal.setGoal(goalDetails.getGoal());
            goal.setEmpInput(goalDetails.getEmpInput());
            goal.setMgrFeedback(goalDetails.getMgrFeedback());
            return goalRepo.save(goal);
        }
        throw new CustomException("goal not found.");
    }

    public void deleteGoal(Long id) {
        goalRepo.deleteById(id);
    }

    public void saveGoal(Goal goal) {
        goalRepo.save(goal);
    }
}
