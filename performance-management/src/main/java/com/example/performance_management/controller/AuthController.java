package com.example.performance_management.controller;

import com.example.performance_management.dto.AuthenticationResponse;
import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.dto.LoginRequestDto;
import com.example.performance_management.dto.RefreshTokenRequest;
import com.example.performance_management.dto.performance.EmployeeLoginResponseDto;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<EmployeeDto> signup(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto signup;
        try {
            signup = authService.employeeSignup(employeeDto);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException("Duplicate username/email.");
        }
        return new ResponseEntity<>(signup, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<EmployeeLoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return new ResponseEntity<>(authService.employeeLogin(loginRequestDto.getUserName(), loginRequestDto.getPassword()), HttpStatus.OK);
    }

    @PostMapping("/refresh/token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthenticationResponse authenticationResponse = authService.refreshToken(refreshTokenRequest);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        authService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Logged out.");
    }
}
