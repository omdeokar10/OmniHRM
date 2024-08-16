package com.example.performance_management.utils;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Supplier;


public class DetailsSetUtils {
    public <T> void setIfNotNull(Supplier<T> getter, Consumer<T> setter) {
        T value = getter.get();
        if (value != null && !(value instanceof String && ((String) value).isEmpty())) {
            setter.accept(value);
        }
    }
}
