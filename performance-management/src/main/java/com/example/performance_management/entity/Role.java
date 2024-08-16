package com.example.performance_management.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {

    @Transient
    public static final String GENERATED_ID = "roleId";
    @Id
    private Long id;
    private String roleName;
    private List<Permission> permissions;

}
