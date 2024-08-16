package com.example.performance_management.service;

import com.example.performance_management.entity.Employee;
import com.example.performance_management.entity.RefreshToken;
import com.example.performance_management.entity.timesheet.Attendance;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.mongoidgen.EmployeeSequenceGeneratorService;
import com.example.performance_management.repo.RefreshTokenRepo;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepo refreshTokenRepository;
    final private EmployeeSequenceGeneratorService employeeSequenceGeneratorService;

    public RefreshTokenService(RefreshTokenRepo refreshTokenRepository, EmployeeSequenceGeneratorService employeeSequenceGeneratorService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.employeeSequenceGeneratorService = employeeSequenceGeneratorService;
    }

    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(generateId());
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    private Long generateId() {
        return employeeSequenceGeneratorService.getAttendanceSequenceNumber
                (Employee.ID_KEY, Employee.ID_VAL, Attendance.GENERATED_ID);
    }

    void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token).orElseThrow(() -> new CustomException("Invalid refresh Token"));
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
