package com.example.petshop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/test-api")
    public String test(){
        return "Tasfia";
    }

    @RequestMapping("/testagain-api")
    public String testagain(){
        return "Shanto";
    }

}
