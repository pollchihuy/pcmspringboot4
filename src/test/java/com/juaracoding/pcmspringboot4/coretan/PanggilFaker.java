package com.juaracoding.pcmspringboot4.coretan;

import com.juaracoding.pcmspringboot4.utils.DataGenerator;

public class PanggilFaker {


    public static void main(String[] args) {
        DataGenerator dataGenerator = new DataGenerator();
        for (int i = 0; i < 10; i++) {
            System.out.println(dataGenerator.dataNoHp());
        }

        char [] chAlfabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        System.out.println("Length : "+chAlfabet.length);
    }
}
