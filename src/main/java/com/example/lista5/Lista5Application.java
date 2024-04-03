package com.example.lista5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Lista5Application {

	public static void main(String[] args) {
		SpringApplication.run(Lista5Application.class, args);
	}

}
