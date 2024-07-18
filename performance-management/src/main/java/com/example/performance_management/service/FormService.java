package com.example.performance_management.service;

import com.example.performance_management.dto.performance.FormDto;
import com.example.performance_management.entity.performance.Form;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.mongoidgen.FormSequenceGeneratorService;
import com.example.performance_management.repo.FormRepo;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class FormService {

    private final FormRepo formRepo;
    private final FormSequenceGeneratorService formSequenceGeneratorService;

    public FormService(FormRepo formRepo, FormSequenceGeneratorService formSequenceGeneratorService) {
        this.formRepo = formRepo;
        this.formSequenceGeneratorService = formSequenceGeneratorService;
    }

    public void createForm(Map<String, String> requestDto) {
        if (checkForSameName(requestDto.get(Form.FORM_NAME_VARIABLE))) {
            throw new CustomException("Form name already exists");
        }
        long id = generateId();
        Form form = new Form(id, requestDto);
        formRepo.save(form);
    }

    public long generateId() {
        return formSequenceGeneratorService.getSequenceNumber(Form.ID_KEY, Form.ID_VAL, Form.GENERATED_ID);
    }

    public boolean checkForSameName(String formName) {
        return formRepo.findByFormNameStartsWith(formName).isPresent();
    }

    public void deleteFormById(Long id) {
        formRepo.deleteById(id);
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



    public Form getFormByName(String formName){
        Optional<Form> optionalForm = formRepo.findByFormNameStartsWith(formName);
        Form form = optionalForm.get();
        return form;
    }

    public FormDto getFormById(Long id) {
        Form form = getForm(id);
        Map<String, String> keyValuePairs = form.getKeyValuePairs();
        keyValuePairs.remove("audience");
        return new FormDto(keyValuePairs);
    }

    public Form getForm(Long id) {
        Optional<Form> optionalForm = formRepo.findById(id);
        if (optionalForm.isEmpty()) {
            throw new CustomException("Form is not present.");
        }
        return optionalForm.get();
    }


}
