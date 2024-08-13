package com.example.performance_management.dto.performance;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FormSubmissionRequestDto {
    private String companyName;
    private String formName;
    private Map<String, String> formData;
}
