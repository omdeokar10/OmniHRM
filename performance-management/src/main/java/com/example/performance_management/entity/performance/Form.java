package com.example.performance_management.entity.performance;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
    private String formName;
    @SuppressWarnings("JpaAttributeTypeInspection") @Field("formdata")
    private Map<String,String> keyValuePairs;

    public Form(String formName, Map<String, String> requestDto) {
        this.formName = formName;
        keyValuePairs = requestDto;
    }

    public Form(long id, Map<String, String> requestDto) {
        this.id = id;
        this.formName = requestDto.get(FORM_NAME_VARIABLE);
        this.keyValuePairs = requestDto;
    }
}


