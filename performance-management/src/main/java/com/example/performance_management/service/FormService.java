package com.example.performance_management.service;

import com.example.performance_management.dto.performance.FieldsDto;
import com.example.performance_management.dto.performance.FormDto;
import com.example.performance_management.dto.performance.PendingFormDto;
import com.example.performance_management.entity.performance.Form;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.mapper.FormMapper;
import com.example.performance_management.mongoidgen.FormSequenceGeneratorService;
import com.example.performance_management.repo.FormRepo;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class FormService {

    private final FormRepo formRepo;
    private final FormMapper mapper = new FormMapper();
    private final FormSequenceGeneratorService formSequenceGeneratorService;

    public FormService(FormRepo formRepo, FormSequenceGeneratorService formSequenceGeneratorService) {
        this.formRepo = formRepo;
        this.formSequenceGeneratorService = formSequenceGeneratorService;
    }

    public void createForm(String companyName, String formName, List<FieldsDto> requestDto) {
        if (checkForDuplicateForm(companyName, formName)) {
            throw new CustomException("Form name already exists");
        }
        long id = generateId();
        Form form = new Form(id, companyName, formName, requestDto);
        formRepo.save(form);
    }

    public long generateId() {
        return formSequenceGeneratorService.getSequenceNumber(Form.ID_KEY, Form.ID_VAL, Form.GENERATED_ID);
    }

    public boolean checkForDuplicateForm(String formName, String companyName) {
        return formRepo.findByFormNameAndCompanyName(formName, companyName).isPresent();
    }

    public void deleteFormById(Long id) {
        formRepo.deleteById(id);
    }

    public void updateFormFromUser(Long id){

    }

//    public List<PendingFormDto> getUserPendingForm(String username) {
//
//        Employee employeeByName = employeeService.getEmployeeByName(username);
//        List<Form> forms = formRepo.findAll();
//        List<Role> roles = employeeByName.getRoles();
//
//        //noinspection UnnecessaryLocalVariable
//        List<PendingFormDto> pendingFormDtos = forms.stream()
//                .filter(form -> checkIfEmployeeSatisfiesCriteriaToFillTheForm(getAudienceFromForm(form), roles))
//                .filter(form -> employeeFormService.checkIfFormFilledByEmployee(username, form.getFormName()))
//                .map(form -> new PendingFormDto(form.getId(), form.getFormName()))
//                .toList();
//
//        return pendingFormDtos;
//    }


    public Form getFormByName(String formName) {
        Optional<Form> optionalForm = formRepo.findByFormName(formName);
        Form form = optionalForm.get();
        return form;
    }

    public Form getForms(Long id) {
        Optional<Form> optionalForm = formRepo.findById(id);
        if (optionalForm.isEmpty()) {
            throw new CustomException("Form is not present.");
        }
        return optionalForm.get();
    }

    public List<PendingFormDto> getForms(String companyName) {
        Optional<List<Form>> optionalForm = formRepo.findByCompanyName(companyName);
        if (optionalForm.isEmpty()) {
            return Collections.emptyList();
        }
        return optionalForm.get().stream().map(form -> new PendingFormDto(form.getId(), form.getFormName())).toList();
    }

}
