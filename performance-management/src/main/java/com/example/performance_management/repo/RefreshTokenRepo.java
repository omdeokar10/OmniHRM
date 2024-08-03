package com.example.performance_management.repo;

import com.example.performance_management.entity.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends MongoRepository<RefreshToken, Long> {
    Optional<RefreshToken>findByToken(String token);
    void deleteByToken(String token);
}
