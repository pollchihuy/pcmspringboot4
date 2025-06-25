package com.juaracoding.pcmspringboot4.util;


import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClassIOC {

    public String getData(){
        return "OK";
    }


    @Scheduled(fixedRate = 60000)
    public void print(){
        System.out.println("Print Per 5 Detik");
    }

    @Async
    public void syncData() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            Thread.sleep(1000);
            System.out.println("Looping ke "+(i+1));
        }
    }
}