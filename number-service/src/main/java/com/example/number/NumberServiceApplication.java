package com.example.number;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableFeignClients
@SpringBootApplication
public class NumberServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NumberServiceApplication.class, args);
	}

}
