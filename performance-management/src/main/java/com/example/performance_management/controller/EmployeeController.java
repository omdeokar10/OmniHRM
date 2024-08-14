package com.example.performance_management.controller;

import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.dto.UserRoleDto;
import com.example.performance_management.entity.role.Role;
import com.example.performance_management.service.AuthService;
import com.example.performance_management.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.StringJoiner;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AuthService authService;
    private final HelperUtil helperUtil;

    public EmployeeController(EmployeeService employeeService, AuthService authService, HelperUtil helperUtil) {
        this.employeeService = employeeService;
        this.authService = authService;
        this.helperUtil = helperUtil;
    }

    @GetMapping("/all")
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

    @GetMapping("/")
    public ResponseEntity<EmployeeDto> getEmployeeByUsername() {
        String loggedInUser = helperUtil.getLoggedInUser();
        EmployeeDto employeeByUsername = employeeService.getEmployeeDtoByUsername(loggedInUser);
        return ResponseEntity.ok(employeeByUsername);
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

    @GetMapping("")
    public ResponseEntity<String> getRolesForUser() {
        String name = helperUtil.getLoggedInUser();
        List<Role> roles = employeeService.getRolesForUser(name);
        StringJoiner stringJoiner = new StringJoiner(",");
        for (Role role : roles) {
            stringJoiner.add(role.toString());
        }
        return ResponseEntity.ok(stringJoiner.toString());
    }

    @PostMapping("/add-role")
    public ResponseEntity<String> addRolesToUser(@RequestBody UserRoleDto userRoleDto) {
        employeeService.addRoleForEmployee(userRoleDto.getRole(), userRoleDto.getUser());
        return ResponseEntity.status(HttpStatus.OK).body("Role: " + userRoleDto.getRole() + " added for user: " + userRoleDto.getUser());
    }

    @DeleteMapping("/remove-role")
    public ResponseEntity<String> deleteRoleForUser(@RequestBody UserRoleDto userRoleDto) {
        employeeService.deleteRoleForEmployee(userRoleDto.getRole(), userRoleDto.getUser());
        return ResponseEntity.ok("OK");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }


}
