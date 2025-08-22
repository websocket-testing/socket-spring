package com.example.kafka_test.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestControllers {
    @GetMapping("/hello")
    public String helloMe(){
        return "Hello wold";
    }
}
