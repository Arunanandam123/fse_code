package com.fse.restaurantapi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class RestaurantsApiApplication {
	private static final Logger logger = LoggerFactory.getLogger(RestaurantsApiApplication.class);
	public static void main(String[] args) {
		logger.info("Starting RestaurantsApiApplication application...");
		SpringApplication.run(RestaurantsApiApplication.class, args);
	}

}
