package com.juaracoding.pcmspringboot4.controller;

import com.juaracoding.pcmspringboot4.config.SMTPConfig;
import com.juaracoding.pcmspringboot4.coretan.ClassStudent;
import com.juaracoding.pcmspringboot4.model.User;
import com.juaracoding.pcmspringboot4.util.ClassIOC;
import com.juaracoding.pcmspringboot4.util.GlobalFunction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

        GlobalFunction.print("EMAIL USERNAME : "+ SMTPConfig.getEmailUsername());
        GlobalFunction.print("EMAIL PASSWORD : "+ SMTPConfig.getEmailPassword());
        GlobalFunction.print("EMAIL HOST : "+ SMTPConfig.getEmailHost());

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

    /** JSON -> Content-Type : application/json */
    @PostMapping("/coba1")
    public String postCoba1(@Valid @RequestBody User user){

        System.out.println("User nya : "+user.getUsername());
        System.out.println("Password nya : "+user.getPassword());
        return "ok";
    }
    //batch request
    /** Multipart -> Content-Type : multipart/form-data */
    @PostMapping("/coba2")
    public String postCoba2(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam MultipartFile file){

        System.out.println("User nya : "+username);
        System.out.println("Password nya : "+password);
        System.out.println("file nya : "+file.getOriginalFilename());

        return "ok broh";
    }
    /** Multipart -> Content-Type : multipart/form-data */
    @PostMapping("/coba3")
    public String postCoba3(
            @RequestParam(value = "user-name") String[] username,//JAVA ? ONTA camelCase
            @RequestParam String[] password,
            @RequestParam MultipartFile[] file){

        if(username.length != 0 && password.length != 0 && file.length != 0){
            int checkArr = username.length;
            if(checkArr < 5 ){
                if(password.length == checkArr && file.length == checkArr){
                    for(int i = 0; i < checkArr; i++){
                        System.out.println("User nya yang ke "+(i+1)+ username[i]);
                        System.out.println("Password nya yang ke "+(i+1)+password[i]);
                        System.out.println("File nya yang ke "+(i+1)+file[i].getOriginalFilename());
                        System.out.println("==================");
                    }
                    return "mantap broh";
                }
            }
        }

        return "gagal broh";
    }


    //localhost:8080/coba4/1/Paul
    @GetMapping("/coba4/{id}/{nama-lengkap}")
    public String getCoba4(
            @PathVariable Integer id,
            @PathVariable(value = "nama-lengkap") String namaLengkap
    ){

        System.out.println("ID : "+id);
        System.out.println("Nama Lengkap : "+namaLengkap);
        return "roger that";
    }
    //localhost:8080/coba4/paul@gmail.com/890712ji0oijahefoirhaorih
//    @GetMapping("/coba4/{email}/{token}")
//    public String getCoba41(
//            @PathVariable Integer id,
//            @PathVariable(value = "nama-lengkap") String namaLengkap
//    ){
//
//        System.out.println("ID : "+id);
//        System.out.println("Nama Lengkap : "+namaLengkap);
//        return "roger that";
//    }

    @GetMapping("/coba5")
    public String getCoba5(
            @RequestParam Long id,
            @RequestParam(value = "nama-lengkap") String namaLengkap,
            @RequestHeader String coba
    ){

        System.out.println("ID : "+id);
        System.out.println("Nama Lengkap : "+namaLengkap);
        System.out.println("Coba Value : "+coba);
        return "roger that coba 5";
    }


    @GetMapping("/coba1")
    public String getCoba1(){


        return "coba1";
    }
}