package com.example.performance_management.mapper;

import com.example.performance_management.dto.performance.GoalDto;
import com.example.performance_management.entity.performance.Goal;

public class GoalMapper {


    public GoalMapper() {
    }

    public GoalDto convertToDto(Goal goal) {
        GoalDto dto = new GoalDto();
        dto.setId(goal.getId());
        dto.setCompanyName(goal.getCompanyName());
        dto.setTitle(goal.getTitle());
        dto.setManagerName(goal.getManagerName());
        dto.setEmployeeName(goal.getEmployeeName());
        dto.setDescription(goal.getDescription());
        dto.setCategory(goal.getCategory());
        dto.setStartDate(goal.getStartDate());
        dto.setEndDate(goal.getEndDate());
        dto.setKpis(goal.getKpis());
        dto.setMilestones(goal.getMilestones());
        dto.setFeedbackNotes(goal.getFeedbackNotes());
        dto.setSelfAssessment(goal.getSelfAssessment());
        dto.setCompleted(goal.isCompleted());
        return dto;
    }

    public Goal convertToGoal(GoalDto dto) {
        Goal goal = new Goal();
        goal.setTitle(dto.getTitle());
        goal.setManagerName(dto.getManagerName());
        goal.setCompanyName(dto.getCompanyName());
        goal.setEmployeeName(dto.getEmployeeName());
        goal.setDescription(dto.getDescription());
        goal.setCategory(dto.getCategory());
        goal.setStartDate(dto.getStartDate());
        goal.setEndDate(dto.getEndDate());
        goal.setKpis(dto.getKpis());
        goal.setMilestones(dto.getMilestones());
        goal.setFeedbackNotes(dto.getFeedbackNotes());
        goal.setSelfAssessment(dto.getSelfAssessment());
        goal.setCompleted(dto.isCompleted());
        return goal;
    }

}
