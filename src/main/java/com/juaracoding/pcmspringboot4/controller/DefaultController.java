package com.juaracoding.pcmspringboot4.controller;

import com.juaracoding.pcmspringboot4.coretan.ClassReturn;
import com.juaracoding.pcmspringboot4.coretan.ClassStudent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/")
public class DefaultController {

    @GetMapping
    public String getData(){
        int x = 1/0;
        return "Hello World";
    }
    @GetMapping("datajson")
    public Object dataJson(){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("data", "Hello World");
        map.put("timestamp", System.currentTimeMillis());
        map.put("version",1);
        map.put("list",new ArrayList<String>());
        map.put("cs",new ClassStudent("Paul","Bogor"));
        for(Map.Entry<String,Object> entry : map.entrySet()){
            System.out.println("key "+entry.getKey()+" - value :"+entry.getValue());
        }
        return map;

//        ClassReturn cr = new ClassReturn();
//        cr.setData("Hello World");
//        cr.setTimestamp(LocalDateTime.now());
//        cr.setVersion(1);
//        System.out.println("Data : " + cr.getData());
//        System.out.println("Timestamp : " + cr.getTimestamp().toString());
//        System.out.println("Version : " + cr.getVersion());
//        return cr;
    }
}