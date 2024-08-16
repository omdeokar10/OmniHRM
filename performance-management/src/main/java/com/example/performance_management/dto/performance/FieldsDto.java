package com.example.performance_management.dto.performance;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FieldsDto {
    private String name;
    private String label;
    private String type;
    private List<OptionsDto> options;
}

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
class OptionsDto {
    public String value;
    public String label;
}
