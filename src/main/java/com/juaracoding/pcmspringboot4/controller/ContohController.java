package com.juaracoding.pcmspringboot4.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("contoh")
public class ContohController {


    @GetMapping
    public String getData(){
        return"/tambahan/ok";
    }

}
