package com.juaracoding.pcmspringboot4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class Pcmspringboot4Application {

	public static void main(String[] args) {
		SpringApplication.run(Pcmspringboot4Application.class, args);
	}

}
