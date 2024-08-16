package com.example.performance_management.dto.performance;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoalDto {

    private Long id;
    private String companyName;
    private String title;
    private String managerName;
    private String employeeName;
    private String description;
    private String category;
    private String startDate;
    private String endDate;
    private String[] kpis;
    private String milestones;
    private String feedbackNotes;
    private String selfAssessment;
    private boolean completed;

}