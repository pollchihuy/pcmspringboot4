package com.juaracoding.pcmspringboot4.coretan;

/*
IntelliJ IDEA 2024.1.4 (Ultimate Edition)
Build #IU-241.18034.62, built on June 21, 2024
@Author pollc a.k.a. Paul Christian
Java Developer
Created on 24/06/2025 19:13
@Last Modified 24/06/2025 19:13
Version 1.0
*/
public class ExceptionExample {

    public static void main(String[] args) {

        int x = 0;

        try {
            x= 1/0;
//            Thread.sleep(10000);

            System.out.println("Proses Selesai");
        } catch (Exception e) {
            System.out.println("Masuk ke Catch");
            return;
        }finally {
            System.out.println("Di Print !!");
            // digunakan untuk menutup koneksi atau awal proses
        }


        String [] strArr = {"x","y"};
        System.out.println(strArr[3]);
    }
}
