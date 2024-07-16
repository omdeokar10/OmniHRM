package com.example.performance_management.service;

import com.example.performance_management.dto.PendingFormDto;
import com.example.performance_management.entity.Employee;
import com.example.performance_management.entity.EmployeeForms;
import com.example.performance_management.entity.Form;
import com.example.performance_management.entity.Role;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.repo.EmployeeFormRepo;
import com.example.performance_management.repo.FormRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeFormService {
    private final EmployeeService employeeService;
    private final EmployeeFormRepo employeeFormRepo;
    private final FormService formService;
    private final FormRepo formRepo;

    public EmployeeFormService(EmployeeService employeeService, EmployeeFormRepo employeeFormRepo, FormService formService, FormRepo formRepo) {
        this.employeeService = employeeService;
        this.employeeFormRepo = employeeFormRepo;
        this.formService = formService;
        this.formRepo = formRepo;
    }

    public void saveFormForEmployee(String username, String formName, Map<String, String>fields){
        if(checkIfFormFilledByEmployee(username, formName)){
            throw new CustomException("Form exists with username");
        }
        if(!employeeService.checkIfUserExistsWithName(username)){
            throw new CustomException("User does not exist with name");
        }
        Form form = new Form(formName, fields);
        EmployeeForms employeeForms = new EmployeeForms(username, formName, form);
        employeeFormRepo.save(employeeForms);
    }

    public List<PendingFormDto> getUserPendingForm(String username) {

        Employee employeeByName = employeeService.getEmployeeByName(username);
        List<Form> forms = formRepo.findAll();
        List<Role> roles = employeeByName.getRoles();

        //noinspection UnnecessaryLocalVariable
        List<PendingFormDto> pendingFormDtos = forms.stream()
                .filter(form -> checkIfEmployeeSatisfiesCriteriaToFillTheForm(getAudienceFromForm(form), roles))
                .filter(form -> !checkIfFormFilledByEmployee(username, form.getFormName()))
                .map(form -> new PendingFormDto(form.getId(), form.getFormName()))
                .toList();

        return pendingFormDtos;
    }

    private boolean checkIfEmployeeSatisfiesCriteriaToFillTheForm(String[] audience, List<Role> roles) {
        for(String aud: audience){
            if(aud.equalsIgnoreCase("all") || roles.contains(aud))
                return true;
        }
        return false;
    }

    public String[] getAudienceFromForm(Form form){
        String audience = form.getKeyValuePairs().get("audience");
        return audience.split(",");
    }

    public boolean checkIfFormFilledByEmployee(String username, String formName){
        Optional<EmployeeForms> employeeForms = employeeFormRepo.findByEmployeeNameAndFormName(username, formName);
        if(employeeForms.isPresent()){
            return true;
        }
        return false;
    }

}
