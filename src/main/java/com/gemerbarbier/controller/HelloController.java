package com.gemerbarbier.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
public class HelloController {
    @GetMapping("/")
    public String helloWorld() {
         return "Hello World";
    }
}