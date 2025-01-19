package com.example.livinglab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.example.livinglab")
public class LivinglabApplication {

	public static void main(String[] args) {
		SpringApplication.run(LivinglabApplication.class, args);
	}

}
