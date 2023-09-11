package com.deokarkaustubh.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long userId;

    @Column(unique = true)
    String username;

    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
