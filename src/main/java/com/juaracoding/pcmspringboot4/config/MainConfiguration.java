package com.juaracoding.pcmspringboot4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;


@Configuration
public class MainConfiguration {

    @Bean
    public Random getRandom(){
        return new Random();
    }
}