package com.juaracoding.pcmspringboot4.coretan;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashSet;

/*
IntelliJ IDEA 2024.1.4 (Ultimate Edition)
Build #IU-241.18034.62, built on June 21, 2024
@Author pollc a.k.a. Paul Christian
Java Developer
Created on 24/06/2025 20:42
@Last Modified 24/06/2025 20:42
Version 1.0
*/
public class ContohHashSet {

    public static void main(String[] args) {
        ArrayList<String> acs2 = new ArrayList<>();
        acs2.add("A");
        acs2.add("B");
        acs2.add("C");
        acs2.add("D");
        acs2.add("D");
        for (String str : acs2) {
            System.out.println(str);
        }
        System.out.println("========================");
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("A");
        hashSet.add("B");
        hashSet.add("C");
        hashSet.add("D");
        hashSet.add("D");
        for (String str : hashSet) {
            System.out.println(str);
        }

        System.out.println("CONTOH OBJECT===============");
        ArrayList<ClassStudent> acs1 = new ArrayList<>();
        ClassStudent cs = new ClassStudent("Paul","Bogor");
        acs1.add(cs);
        acs1.add(cs);
        acs1.add(cs);
        acs1.add(cs);
        for (ClassStudent c: acs1) {
            System.out.println(c);
        }
        System.out.println("=============");
        HashSet<ClassStudent> hashSet1 = new HashSet<>();
        hashSet1.add(cs);
        hashSet1.add(cs);
        hashSet1.add(cs);
        hashSet1.add(cs);
        for (ClassStudent c: hashSet1) {
            System.out.println(c);
        }




    }
}
