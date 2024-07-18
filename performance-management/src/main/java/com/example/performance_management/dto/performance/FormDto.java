package com.example.performance_management.dto.performance;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormDto {

    Map<String, String> fields = new HashMap<>();

    public void addField(String key, String value){
        fields.put(key, value);
    }

    public void getField(String key){
        fields.get(key);
    }

}
