package com.example.performance_management.mapper;

import com.example.performance_management.dto.performance.FormDto;
import com.example.performance_management.entity.performance.Form;

public class FormMapper {

    public Form convertToEntity(FormDto formDto) {
        Form form = new Form();
        form.setFormName(formDto.getFormName());
        form.setTemplateFields(formDto.getFields());
        form.setCompanyName(formDto.getCompanyName());
        return form;
    }

    public FormDto convertToDto(Form form) {
        FormDto formDto = new FormDto();
        formDto.setId(form.getId());
        formDto.setFields(form.getTemplateFields());
        formDto.setFormName(form.getFormName());
        formDto.setCompanyName(form.getCompanyName());
        return formDto;
    }

}
