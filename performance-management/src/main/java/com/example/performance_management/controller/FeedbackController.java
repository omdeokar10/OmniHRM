package com.example.performance_management.controller;

import com.example.performance_management.entity.Goal;
import com.example.performance_management.service.GoalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  Should be controller by a manager kinda entity.
 */
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final GoalService goalService;

    public FeedbackController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping("/goal")
    public void feedbackForGoals(Long goalId, String feedback){
        Goal goal = goalService.getGoalById(goalId).get();
        goal.setMgrFeedback(feedback);
        goalService.saveGoal(goal);
    }


}
