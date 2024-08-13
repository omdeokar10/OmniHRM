package com.example.performance_management.entity.performance;


import com.example.performance_management.dto.performance.FieldsDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "form")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Form {

    @Transient
    public static final String ID_VAL = "form_seq";
    @Transient
    public static final String GENERATED_ID = "formId"; //IdSequence.formId
    @Transient
    public static final String ID_KEY = "id"; //IdSequence.formId
    @Transient
    public static final String FORM_NAME_VARIABLE = "formName"; //IdSequence.formId
    @Id
    private Long id;
    private String companyName;
    private String formName;
    private List<FieldsDto> templateFields;
    private Map<String, String> userFilledInputs;

    public Form(Long id, String companyName, String formName, List<FieldsDto> templateFields) {
        this.id = id;
        this.companyName = companyName;
        this.formName = formName;
        this.templateFields = templateFields;
    }

    public Form(Long id, String companyName, String formName, Map<String, String> userFilledInputs) {
        this.id = id;
        this.companyName = companyName;
        this.formName = formName;
        this.userFilledInputs = userFilledInputs;
    }
}


