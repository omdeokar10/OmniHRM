package com.example.performance_management.mapper;

import com.example.performance_management.dto.GoalDto;
import com.example.performance_management.entity.Goal;

public class GoalMapper {
    public static GoalDto toDTO(Goal goal) {
        GoalDto goalDTO = new GoalDto();
        goalDTO.setUsername(goal.getUsername());
        goalDTO.setGoal(goal.getGoal());
        goalDTO.setEmpInput(goal.getEmpInput());
        goalDTO.setMgrFeedback(goal.getMgrFeedback());
        return goalDTO;
    }

    public static Goal toEntity(GoalDto goalDTO) {
        Goal goal = new Goal();
        goal.setUsername(goalDTO.getUsername());
        goal.setGoal(goalDTO.getGoal());
        goal.setEmpInput(goalDTO.getEmpInput());
        goal.setMgrFeedback(goalDTO.getMgrFeedback());
        return goal;
    }
}
