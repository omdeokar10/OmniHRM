package com.example.performance_management.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "role")
@Data
@NoArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    private Long roleId;

    private String roleName;

}
