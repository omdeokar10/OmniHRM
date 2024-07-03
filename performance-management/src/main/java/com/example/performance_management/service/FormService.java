package com.example.performance_management.service;

import com.example.performance_management.entity.Form;
import com.example.performance_management.mongoidgen.FormSequenceGeneratorService;
import com.example.performance_management.repo.FormRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FormService {

    private final FormRepo formRepo;
    private final FormSequenceGeneratorService formSequenceGeneratorService;

    public FormService(FormRepo formRepo, FormSequenceGeneratorService formSequenceGeneratorService) {
        this.formRepo = formRepo;
        this.formSequenceGeneratorService = formSequenceGeneratorService;
    }

    public void createForm(Map<String, String> requestDto)
    {
        long id = formSequenceGeneratorService.getSequenceNumber(Form.ID_KEY, Form.ID_VAL, Form.GENERATED_ID);
        Form form = new Form(id, requestDto);
        formRepo.save(form);
    }

    public List<Form> getForm() {
        List<Form> all = formRepo.findAll();
        return all;
    }

}
