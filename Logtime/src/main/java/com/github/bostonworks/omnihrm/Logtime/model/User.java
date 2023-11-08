package com.github.bostonworks.omnihrm.Logtime.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "userid")
    private Long userId;

    @Column(unique = true)
    String username;

    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
