package com.buddybank.mysuperbank.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("customer")
public class CustomerController {

    @GetMapping("/deneme")
    public String customerList(){
        return "deneme";
    }
}
