package com.example.performance_management.entity;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Document(collection = "form")
@Data
@NoArgsConstructor
@Getter
@Setter
public class Form {

    @Transient
    public static final String ID_VAL = "form_seq";
    @Transient
    public static final String GENERATED_ID = "formId"; //IdSequence.formId
    @Transient
    public static final String ID_KEY = "id"; //IdSequence.formId
    @Id
    private Long id;
    @SuppressWarnings("JpaAttributeTypeInspection") @Field("formdata")
    private Map<String,String> keyValuePairs;

    public Form(Map<String, String> requestDto) {
        keyValuePairs = requestDto;
    }

    public Form(long id, Map<String, String> requestDto) {
        this.id = id;
        this.keyValuePairs = requestDto;
    }
}


