package com.juaracoding.pcmspringboot4.util;

import com.juaracoding.pcmspringboot4.config.OtherConfig;
import org.springframework.validation.BindingResult;

import java.util.regex.Pattern;

/*
IntelliJ IDEA 2024.1.4 (Ultimate Edition)
Build #IU-241.18034.62, built on June 21, 2024
@Author pollc a.k.a. Paul Christian
Java Developer
Created on 30/06/2025 21:29
@Last Modified 30/06/2025 21:29
Version 1.0
*/
public class GlobalFunction {

    public static void print(Object object){
        if(OtherConfig.getEnablePrintConsole().equals("y")){
            System.out.println(object);
        }
    }
    public static Boolean matchingPattern(String value,String regex
                                       ){
        Boolean isValid = Pattern.compile(regex).matcher(value).find();
        if(!isValid){
            return false;
        }
        return true;
    }
}
