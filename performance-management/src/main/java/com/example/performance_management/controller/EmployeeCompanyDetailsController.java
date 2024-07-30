package com.example.performance_management.controller;

import com.example.performance_management.dto.EmployeeCompanyDetailsDto;
import com.example.performance_management.dto.EmployeeHierarchyDto;
import com.example.performance_management.service.EmployeeCompanyDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employeesdetails")
@CrossOrigin("*")
public class EmployeeCompanyDetailsController {

    private final EmployeeCompanyDetailsService employeeCompanyDetailsService;

    public EmployeeCompanyDetailsController(EmployeeCompanyDetailsService employeeCompanyDetailsService) {
        this.employeeCompanyDetailsService = employeeCompanyDetailsService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<EmployeeCompanyDetailsDto> getEmployeeDetailsById(@PathVariable String email){
        EmployeeCompanyDetailsDto employeeCompanyDetails = employeeCompanyDetailsService.getDetailsForEmployee(email);
        return ResponseEntity.ok(employeeCompanyDetails);
    }

    @PostMapping("/")
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeCompanyDetailsDto employeeDto) {
        employeeCompanyDetailsService.saveDetailsForEmployee(employeeDto);
        return ResponseEntity.ok("Created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody EmployeeCompanyDetailsDto employeeDetails) {
        employeeCompanyDetailsService.updateDetailsForEmployee(id, employeeDetails);
        return ResponseEntity.ok("Updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeCompanyDetailsService.deleteEmployeeDetails(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hierarchy/{email}")
    public List<EmployeeHierarchyDto> getHierarchy(@PathVariable String email){
        return employeeCompanyDetailsService.getHierarchy(email);
    }

}
