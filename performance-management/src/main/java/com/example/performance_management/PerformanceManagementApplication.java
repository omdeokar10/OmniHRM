package com.example.performance_management;

import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.entity.Employee;
import com.example.performance_management.mongoidgen.EmployeeSequenceGeneratorService;
import com.example.performance_management.repo.EmployeeRepo;
import com.example.performance_management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.awt.print.Book;
import java.util.Date;

@SpringBootApplication
public class PerformanceManagementApplication {

    @Autowired
    EmployeeSequenceGeneratorService employeeSequenceGeneratorService;
    @Autowired
    EmployeeRepo employeeRepo;

    public static void main(String[] args) {
        SpringApplication.run(PerformanceManagementApplication.class, args);
    }


    @PostMapping("/saveBook")
    public void save(@RequestBody EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setFullName(employeeDto.getFullName());
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        employee.setRole(employeeDto.getRole());
        employee.setTeam(employeeDto.getTeam());
        employee.setId(employeeSequenceGeneratorService.getEmployeeSequenceNumber(Employee.ID_KEY, Employee.ID_VAL, Employee.GENERATED_ID));
        employeeRepo.save(employee);
    }

}
