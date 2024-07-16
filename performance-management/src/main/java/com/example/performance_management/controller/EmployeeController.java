package com.example.performance_management.controller;

import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.service.AuthService;
import com.example.performance_management.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AuthService authService;

    public EmployeeController(EmployeeService employeeService, AuthService authService) {
        this.employeeService = employeeService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllViewableEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        employeeService.getEmployeeById(id);
        EmployeeDto employeeDto = employeeService.getViewableEmployeeById(id);
        return ResponseEntity.ok(employeeDto);
    }

    @PostMapping
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
        return authService.employeeSignup(employeeDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDetails) {
        EmployeeDto updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

}
