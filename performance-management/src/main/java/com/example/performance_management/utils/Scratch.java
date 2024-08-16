package com.example.performance_management.utils;

import com.example.performance_management.exception.GlobalExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Scratch {

    public static void main(String[] args) {

        LocalDate parse = LocalDate.now();
        System.out.println(parse);

        String s = parse.toString();
        System.out.println(s);

    }

}
