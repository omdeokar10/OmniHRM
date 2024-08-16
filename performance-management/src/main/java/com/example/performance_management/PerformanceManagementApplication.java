package com.example.performance_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PerformanceManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PerformanceManagementApplication.class, args);
    }


}
