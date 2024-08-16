package com.example.performance_management.service;

import com.example.performance_management.dto.performance.FormDto;
import com.example.performance_management.dto.performance.PendingFormDto;
import com.example.performance_management.entity.Employee;
import com.example.performance_management.entity.performance.EmployeeForms;
import com.example.performance_management.entity.performance.Form;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.mapper.FormMapper;
import com.example.performance_management.repo.EmployeeFormRepo;
import com.example.performance_management.repo.FormRepo;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeFormService {
    private final EmployeeService employeeService;
    private final EmployeeFormRepo employeeFormRepo;
    private final FormService formService;
    private final FormRepo formRepo;
    private final FormMapper formMapper = new FormMapper();

    public EmployeeFormService(EmployeeService employeeService, EmployeeFormRepo employeeFormRepo, FormService formService, FormRepo formRepo) {
        this.employeeService = employeeService;
        this.employeeFormRepo = employeeFormRepo;
        this.formService = formService;
        this.formRepo = formRepo;
    }

    public void saveFormForEmployee(String username, String companyName, String formName, Map<String, String> fields) {

        if (checkIfFormFilledByEmployee(companyName, username, formName)) {
            throw new CustomException("Form exists with username");
        }
        if (!employeeService.checkIfUserExistsWithName(username)) {
            throw new CustomException("User does not exist with name");
        }
        Form form = new Form(formService.generateId(), companyName, formName, fields);
        EmployeeForms employeeForms = new EmployeeForms(username, companyName, formName, form);
        employeeFormRepo.save(employeeForms);
    }

    public List<PendingFormDto> getUserPendingForm(String username) {

        Employee employee = employeeService.getEmployeeByName(username);
        String empName = employee.getUserName();
        String companyName = employee.getCompanyName();
        Optional<List<Form>> formsByCompany = formRepo.findByCompanyName(companyName);
        if(formsByCompany.isEmpty()){
            return Collections.emptyList();
        }

        Optional<EmployeeForms> filledOutForms = employeeFormRepo.findByCompanyNameAndEmployeeName(companyName, empName);
        if(filledOutForms.isEmpty()){
            return formsByCompany.get().stream().
                    filter((form) -> true). // future eligibility check - team // checkIfEmployeeSatisfiesCriteriaToFillTheForm
                    map(form -> new PendingFormDto(form.getId(), form.getFormName())).toList();
        }

        //noinspection UnnecessaryLocalVariable
        List<PendingFormDto> pendingFormDtos = formsByCompany.get().stream()
                .filter(form -> checkIfEmployeeSatisfiesCriteriaToFillTheForm())
                .filter(form -> !checkIfFormFilledByEmployee(companyName, empName, form.getFormName()))
                .map(form -> new PendingFormDto(form.getId(), form.getCompanyName())).toList();

        return pendingFormDtos;
    }

    private boolean checkIfEmployeeSatisfiesCriteriaToFillTheForm() {
        return true;//todo: segregate by team.
    }

    public String[] getAudienceFromForm(Form form) {
        String audience = "";//todo:correct
        return audience.split(",");
    }

    public boolean checkIfFormFilledByEmployee(String companyName, String username, String formName) {
        Optional<EmployeeForms> employeeForms = employeeFormRepo.
                findByCompanyNameAndEmployeeNameAndFormName(companyName, username, formName);
        return employeeForms.isPresent();
    }

    public FormDto getFormById(Long id) {
        Optional<Form> optionalForm = formRepo.findById(id);
        if (optionalForm.isPresent()) {
            return formMapper.convertToDto(optionalForm.get());
        }
        throw new CustomException("Form does not exist");
    }
}
