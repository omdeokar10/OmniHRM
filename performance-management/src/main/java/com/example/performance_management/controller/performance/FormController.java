package com.example.performance_management.controller.performance;

import com.example.performance_management.dto.FormDto;
import com.example.performance_management.dto.PendingFormDto;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.service.EmployeeFormService;
import com.example.performance_management.service.EmployeeService;
import com.example.performance_management.service.FormService;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/form")
@CrossOrigin("*")
public class FormController {

    private final FormService formService;
    private final EmployeeFormService employeeFormService;
    private final EmployeeService employeeService;

    public FormController(FormService formService, EmployeeFormService employeeFormService, EmployeeService employeeService) {
        this.formService = formService;
        this.employeeFormService = employeeFormService;
        this.employeeService = employeeService;
    }

    @PostMapping
    public void submitForm(@RequestBody Map<String, String> requestDto) {
        formService.createForm(requestDto);
    }

    @PostMapping("/{username}")
    public void submitFormForAUser(@PathVariable String username, @RequestBody Map<String, String> requestDto) {
        requestDto.remove("audience");// todo: remove.
        requestDto.remove("dueDate");// todo: remove.
        String formName = requestDto.remove("formName");// todo: remove.
        employeeFormService.saveFormForEmployee(username, formName, requestDto);
    }

    @GetMapping("/user/{username}")
    public List<PendingFormDto> getAllPendingFormsForUser(@PathVariable String username) {
        return employeeFormService.getUserPendingForm(username);
    }

    @GetMapping("/{id}")
    public FormDto getFormById(@PathVariable Long id) {
        return formService.getFormById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteForm(@PathVariable Long id) {
        formService.deleteFormById(id);
    }

}
