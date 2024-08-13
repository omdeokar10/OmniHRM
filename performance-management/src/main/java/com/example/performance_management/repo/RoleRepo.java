package com.example.performance_management.repo;

import com.example.performance_management.entity.role.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepo extends MongoRepository<Role, Long> {
    Optional<Role> findByRoleNameStartsWith(String name);
}
