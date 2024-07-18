package com.example.performance_management.entity.performance;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "goal")
@Data
@NoArgsConstructor
@Getter
@Setter
public class Goal {

    @Transient
    public static final String GENERATED_ID = "goalId"; //IdSequence.formId
    @Id
    private Long id;
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
