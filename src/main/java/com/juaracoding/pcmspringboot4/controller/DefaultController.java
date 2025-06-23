package com.juaracoding.pcmspringboot4.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/")
public class DefaultController {

    @GetMapping
    public String getData(){
        return "Hello World";
    }
    @GetMapping("datajson")
    public Map<String, Object> dataJson(){
        Map<String, Object> map = new HashMap<>();
        map.put("data", "Hello World");
        map.put("timestamp", System.currentTimeMillis());
        map.put("version", "1.0.0");
        return map;
    }
}
