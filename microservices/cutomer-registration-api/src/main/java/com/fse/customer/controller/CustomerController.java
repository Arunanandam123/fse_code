package com.fse.customer.controller;

import javax.validation.Valid;

import org.apache.http.HttpStatus;
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
import com.fse.customer.model.CustomerRequest;
import com.fse.customer.model.LoginRequest;
import com.fse.customer.service.CustomUserDetailsService;
import com.fse.customer.service.CustomerService;
import com.fse.customer.validator.ValidatorUtil;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
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
		if (!ValidatorUtil.isValidPhoneNumber(customerRequest.getPhoneNumber())) {
			throw new CustomConstraintException("INVALID-Phone-Number", "Invalid phone number");
		}
		customerService.registerCustomer(customerRequest);
		return ResponseEntity.ok("Registration successful.");
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
			String token = jwtTokenProvider.createToken(userDetails);
			return ResponseEntity.ok(token);
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).build();
		}
	}

}
