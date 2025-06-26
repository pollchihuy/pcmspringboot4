package com.juaracoding.pcmspringboot4.coretan;

import java.util.Scanner;
import java.util.regex.Pattern;

public class RegexExample {

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Masukkan Kata : ");
            String input = sc.nextLine();
            Boolean isOk = checkFormat("^[A-Za-z0-9]{2,}$",input);

            if(!isOk){
                System.out.println("BERHENTI !!!");
                return;
            }

            System.out.println("Script ini tidak Jalan");

        }

    private static Boolean checkFormat(String regex,String value)  {

        try {
            Thread.sleep(1000);
            int x = 1/0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        Boolean isValid = true;
        if(Pattern.compile(regex)
                .matcher(value)
                .matches())
        {
            return true;
        }else {
            return false;
        }
    }
}