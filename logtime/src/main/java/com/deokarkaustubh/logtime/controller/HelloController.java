package com.deokarkaustubh.logtime.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class HelloController {

    static int num = 0;

    @GetMapping("")
    public int hello() {
        return num++;
    }

}
