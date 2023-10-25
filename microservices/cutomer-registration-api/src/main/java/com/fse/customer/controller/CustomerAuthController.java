package com.fse.customer.controller;

import javax.validation.Valid;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse.customer.config.JwtTokenProvider;
import com.fse.customer.exception.CustomConstraintException;
import com.fse.customer.model.AuthResponse;
import com.fse.customer.model.CustomerRequest;
import com.fse.customer.model.LoginRequest;
import com.fse.customer.service.CustomUserDetailsService;
import com.fse.customer.service.CustomerService;
import com.fse.customer.validator.ValidatorUtil;

@RestController
@RequestMapping("/api/customers")
public class CustomerAuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerAuthController.class);
	@Autowired
	private CustomerService customerService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping(value = "/register", consumes = { "application/json" })
	public ResponseEntity<?> registerCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
		logger.info("Request to register an user.");
		if (!ValidatorUtil.isValidPhoneNumber(customerRequest.getPhoneNumber())) {
			throw new CustomConstraintException("INVALID-Phone-Number", "Invalid phone number");
		}
		customerService.registerCustomer(customerRequest);
		return ResponseEntity.ok("Registration successful.");
	}

	@PostMapping(value = "/login", consumes = { "application/json" })
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {		
		logger.info("Login request initiated.");
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
			String token = jwtTokenProvider.createToken(userDetails);
			AuthResponse authResponse = new AuthResponse();
			authResponse.setToken(token);
			return ResponseEntity.ok(authResponse);
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).build();
		}
	}

}
