package com.example.performance_management.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Company {
    @Transient
    public static final String ID_VAL = "company_seq";
    @Transient
    public static final String GENERATED_ID = "companyId"; //IdSequence.formId
    @Transient
    public static final String ID_KEY = "company_id"; //IdSequence.formId
    @Id
    public Long id;
    private String companyName;
    private String companyDomain; //used for email ids.

}
