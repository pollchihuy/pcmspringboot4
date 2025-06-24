package com.juaracoding.pcmspringboot4.coretan;

/*
IntelliJ IDEA 2024.1.4 (Ultimate Edition)
Build #IU-241.18034.62, built on June 21, 2024
@Author pollc a.k.a. Paul Christian
Java Developer
Created on 24/06/2025 20:00
@Last Modified 24/06/2025 20:00
Version 1.0
*/
public class CollectionArray {

    public static void main(String[] args) {
        ClassStudent [] csArr = new ClassStudent[2];
        csArr[0] = new ClassStudent("Paul","Bogor");
        csArr[1] = new ClassStudent("Kevin","Jakarta");
//        for (ClassStudent cs : csArr) {
//            System.out.println(cs);
////            System.out.println(cs.getNama());
////            System.out.println(cs.getAlamat());
//            System.out.println("========================");
//        }

        StringBuilder sBuild = new StringBuilder();
        String s = sBuild.
                append("A").append(1).append("B").append(2).append("C").toString();
        sBuild.setLength(0);
        String c = new StringBuilder().append("v").append("h").toString();

        System.out.println(csArr[0]);
        System.out.println(csArr[0].getNama());
        System.out.println(csArr[0].getAlamat());
        System.out.println("========================");
        System.out.println(csArr[1]);
        System.out.println(csArr[1].getNama());
        System.out.println(csArr[1].getAlamat());
        csArr = null;
        System.out.println(csArr[0]);
        System.out.println(csArr[0].getNama());
        System.out.println(csArr[0].getAlamat());
        System.out.println("========================");
        System.out.println(csArr[1]);
        System.out.println(csArr[1].getNama());
        System.out.println(csArr[1].getAlamat());
    }
}
