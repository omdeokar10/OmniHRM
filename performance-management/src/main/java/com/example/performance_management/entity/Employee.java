package com.example.performance_management.entity;

import com.example.performance_management.entity.role.Role;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "employee")
@Data
@NoArgsConstructor
@Getter
@Setter
public class Employee {

    @Transient
    public static final String ID_VAL = "employee_seq";
    @Transient
    public static final String GENERATED_ID = "employeeId"; //IdSequence.formId
    @Transient
    public static final String ID_KEY = "employeeid"; //IdSequence.formId
    @Id
    private Long id;
    private String userName;
    private String password;
    private String email;
    private List<Role> roles;
    private String companyName;
    public boolean isEnabled = true;
}
