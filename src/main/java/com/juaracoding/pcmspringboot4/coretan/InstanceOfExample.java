package com.juaracoding.pcmspringboot4.coretan;

/*
IntelliJ IDEA 2024.1.4 (Ultimate Edition)
Build #IU-241.18034.62, built on June 21, 2024
@Author pollc a.k.a. Paul Christian
Java Developer
Created on 07/07/2025 19:42
@Last Modified 07/07/2025 19:42
Version 1.0
*/
public class InstanceOfExample {

    public static void main(String[] args) {
        Object x = 10.0f;

        if(x instanceof Integer){
            System.out.println("Ini Integer");
        }else if(x instanceof String){
            System.out.println("Ini String");
        }else if(x instanceof Double){
            System.out.println("Ini Double");
        }else if(x instanceof Boolean){
            System.out.println("Ini Boolean");
        }else if(x instanceof Character){
            System.out.println("Ini Character");
        }else if(x instanceof Float){
            System.out.println("Ini Float");
        }else if(x instanceof Long){
            System.out.println("Ini Long");
        }
    }
}
