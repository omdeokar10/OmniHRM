package com.example.performance_management.controller;

import com.example.performance_management.entity.Form;
import com.example.performance_management.service.FormService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/form/submit")
@CrossOrigin("*")
public class FormController {

    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @PostMapping
    public void submitForm(@RequestBody Map<String,String> requestDto){
        formService.createForm(requestDto);
    }

    @GetMapping
    public List<Form> getAllForm(){
        return formService.getForm();
    }


}
