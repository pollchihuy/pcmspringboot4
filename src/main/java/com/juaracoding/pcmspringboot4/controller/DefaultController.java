package com.juaracoding.pcmspringboot4.controller;

import com.juaracoding.pcmspringboot4.coretan.ClassReturn;
import com.juaracoding.pcmspringboot4.coretan.ClassStudent;
import com.juaracoding.pcmspringboot4.util.ClassIOC;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/")
public class DefaultController {


    @Autowired
    ClassIOC classIOC;

    @Autowired
    Random random;

    @Value("cumi.goreng")
    String cumiGoreng ;

    //localhost:8080
    @GetMapping
    public String getData(){

        try {
            classIOC.syncData();
        } catch (InterruptedException e) {
            System.out.println("Error "+e.getMessage());
        }
        return classIOC.getData()+random.nextInt(100)+"---"+cumiGoreng;
    }

    //localhost:8080/datajson
    @GetMapping("datajson")
    public Object dataJson(HttpServletRequest request){
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