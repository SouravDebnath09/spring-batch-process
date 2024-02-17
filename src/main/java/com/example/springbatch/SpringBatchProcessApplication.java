package com.example.springbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchProcessApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchProcessApplication.class, args);
	}

}

