package com.example.performance_management.dto.performance;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FormGenerationRequestDto {
    private String companyName;
    private String formName;
    private List<FieldsDto> formData;
}
