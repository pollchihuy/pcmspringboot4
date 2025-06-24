package com.juaracoding.pcmspringboot4.coretan;

/*
IntelliJ IDEA 2024.1.4 (Ultimate Edition)
Build #IU-241.18034.62, built on June 21, 2024
@Author pollc a.k.a. Paul Christian
Java Developer
Created on 24/06/2025 19:49
@Last Modified 24/06/2025 19:49
Version 1.0
*/
public class ContohThrow {

    public static void main(String[] args) {
        try {
            execute(13);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("kode berjalan dengan benar");
    }

    private static void execute(Integer x ) throws InterruptedException{
        Thread.sleep(1000L);
    }
}
