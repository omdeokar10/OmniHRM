package com.example.performance_management.controller;

import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.dto.LoginRequestDto;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.service.AuthService;
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
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        return new ResponseEntity<>(authService.employeeLogin(loginRequestDto.getUserName(), loginRequestDto.getPassword()), HttpStatus.OK);
    }
}
