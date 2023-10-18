package com.fse.restaurantapi.service;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.fse.restaurantapi.query.CustomerRequest;
import com.fse.restaurantapi.query.LoginRequest;

@FeignClient(name = "CUSTOMER-AUTH-API")
public interface UserRegistrationService {

	@PostMapping(value = "/api/customers/register")
	public void registerUser(CustomerRequest customerRequest);

	@PostMapping(value = "/api/customers/login")
	public String loginUser(@Valid LoginRequest loginRequest);

}
