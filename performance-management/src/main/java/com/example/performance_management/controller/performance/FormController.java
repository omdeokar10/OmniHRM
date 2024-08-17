package com.example.performance_management.controller.performance;

import com.example.performance_management.utils.HelperUtil;
import com.example.performance_management.dto.performance.FormDto;
import com.example.performance_management.dto.performance.FormGenerationRequestDto;
import com.example.performance_management.dto.performance.FormSubmissionRequestDto;
import com.example.performance_management.dto.performance.PendingFormDto;
import com.example.performance_management.service.EmployeeFormService;
import com.example.performance_management.service.FormService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/form")
@CrossOrigin("*")
public class FormController {

    private final FormService formService;
    private final EmployeeFormService employeeFormService;
    private final HelperUtil helperUtil;

    public FormController(FormService formService, EmployeeFormService employeeFormService, HelperUtil helperUtil) {
        this.formService = formService;
        this.employeeFormService = employeeFormService;
        this.helperUtil = helperUtil;
    }

    @PostMapping("/")
    public void submitForm(@RequestBody FormGenerationRequestDto requestDto) {
        formService.createForm(requestDto.getCompanyName(), requestDto.getFormName(), requestDto.getFormData());
    }

    @GetMapping("/company/{companyName}")
    public List<PendingFormDto> getAllFormsForCompany(@PathVariable String companyName) {
        return getAllPendingFormsForUser();
    }

    @PutMapping("/{id}")
    public void submitFormForAUser(@PathVariable String id, @RequestBody FormSubmissionRequestDto requestDto) {
        System.out.println(id);
        String username = helperUtil.getLoggedInUser();
        employeeFormService.saveFormForEmployee(username, requestDto.getCompanyName(), requestDto.getFormName(), requestDto.getFormData());
    }

    @GetMapping("/user")
    public List<PendingFormDto> getAllPendingFormsForUser() {
        String username = helperUtil.getLoggedInUser();
        return employeeFormService.getUserPendingForm(username);
    }

    @GetMapping("/id/{id}")
    public FormDto getFormById(@PathVariable Long id) {
        return employeeFormService.getFormById(id);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('COMPANYADMIN','ADMIN')")
    public void deleteForm(@PathVariable Long id) {
        String loggedInUser = helperUtil.getLoggedInUser();
        formService.deleteFormById(loggedInUser, id);
    }

}
