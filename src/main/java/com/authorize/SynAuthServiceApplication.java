package com.authorize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@OpenAPIDefinition
@ComponentScan(basePackages = "com.authorize")
@SpringBootApplication
public class SynAuthServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SynAuthServiceApplication.class, args);
	}
}