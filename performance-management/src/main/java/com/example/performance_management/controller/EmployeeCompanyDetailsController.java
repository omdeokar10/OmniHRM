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
    private final HelperUtil helperUtil;

    public EmployeeCompanyDetailsController(EmployeeCompanyDetailsService employeeCompanyDetailsService, HelperUtil helperUtil) {
        this.employeeCompanyDetailsService = employeeCompanyDetailsService;
        this.helperUtil = helperUtil;
    }

    @GetMapping("/")
    public ResponseEntity<EmployeeCompanyDetailsDto> getCompanyDetailsForEmp() {
        String loggedInUser = helperUtil.getLoggedInUser();
        EmployeeCompanyDetailsDto employeeCompanyDetails = employeeCompanyDetailsService.getDetailsForEmployeeByUsername(loggedInUser);
        return ResponseEntity.ok(employeeCompanyDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeCompanyDetailsDto> getCompanyDetailsForEmp(@PathVariable String id) {
        EmployeeCompanyDetailsDto employeeCompanyDetails = employeeCompanyDetailsService.getDetailsForEmployeeById(Long.parseLong(id));
        return ResponseEntity.ok(employeeCompanyDetails);
    }

    @GetMapping("/all/{companyName}")
    public ResponseEntity<List<EmployeeCompanyDetailsDto>> getAllEmployeeForCompany(@PathVariable String companyName) {
        return ResponseEntity.ok(employeeCompanyDetailsService.getEmployeesByCompany(companyName));
    }

    @PostMapping("/")
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeCompanyDetailsDto employeeDto) {
        employeeCompanyDetailsService.createEmployeeForCompany(employeeDto);
        return ResponseEntity.ok("Created");
    }

    @GetMapping("/hierarchy")
    public List<EmployeeHierarchyDto> getHierarchy() {
        String loggedInUser = helperUtil.getLoggedInUser();
        return employeeCompanyDetailsService.getHierarchy(loggedInUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompanyDetailsForEmployee(@PathVariable Long id,
                                                                  @RequestBody EmployeeCompanyDetailsDto employeeDetails)
    {
        employeeCompanyDetailsService.updateDetailsForEmployee(id, employeeDetails);
        return ResponseEntity.ok("Updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeCompanyDetailsService.deleteEmployeeDetails(id);
        return ResponseEntity.noContent().build();
    }

}
