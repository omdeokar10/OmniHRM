package com.example.performance_management.dto.performance;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormDto {
    private Long id;
    private String formName;
    private String companyName;
    private List<FieldsDto> fields;
}
