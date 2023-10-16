package com.fse.restaurantapi.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.fse.restaurantapi.query.CustomerRequest;

@FeignClient(name = "CUSTOMER-REGISTRATION-API")
public interface UserRegistrationService {

	@PostMapping(value = "/api/customers/register")
	public void registerUser(CustomerRequest customerRequest);

}
