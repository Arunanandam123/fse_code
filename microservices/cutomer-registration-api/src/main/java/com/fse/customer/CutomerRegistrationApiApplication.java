package com.fse.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CutomerRegistrationApiApplication {

	private static final Logger logger = LoggerFactory.getLogger(CutomerRegistrationApiApplication.class);
	public static void main(String[] args) {
		logger.info("Starting CutomerRegistrationApiApplication application...");
		SpringApplication.run(CutomerRegistrationApiApplication.class, args);
	}

}
