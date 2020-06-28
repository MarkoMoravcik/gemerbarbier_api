package com.gemerbarbier.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String helloWorld() {
         return "Hello World";
    }

    @GetMapping("/home")
    public String home() {
         return "home";
    }

}