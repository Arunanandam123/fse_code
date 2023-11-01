package com.fse.restaurantapi.service;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.fse.restaurantapi.query.AuthResponse;
import com.fse.restaurantapi.query.CustomerRequest;
import com.fse.restaurantapi.query.LoginRequest;

@FeignClient(name = "customer-auth-api", url = "http://customer-auth-api:8083")
public interface UserRegistrationService {

	@PostMapping(value = "/api/customers/register")
	public void registerUser(CustomerRequest customerRequest);

	@PostMapping(value = "/api/customers/login")
	public AuthResponse loginUser(@Valid LoginRequest loginRequest);

}
