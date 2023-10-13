package com.fse.restaurantapi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RestaurantsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantsApiApplication.class, args);
	}

}
