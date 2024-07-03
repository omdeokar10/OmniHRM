package com.example.performance_management.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "goal")
@Data
@NoArgsConstructor
@Getter
@Setter
public class Goal {

    @Id
    private Long id;

    private String username;

    private String goal;

    private String empInput;

    private String mgrFeedback;
}
