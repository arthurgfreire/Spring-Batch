package com.springbatch.wd.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class BatchServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BatchServiceApplication.class);
    	app.run(args);            	
	}
}
