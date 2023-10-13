package com.fse.customer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse.customer.exception.CustomConstraintException;
import com.fse.customer.model.CustomerRequest;
import com.fse.customer.service.CustomerService;
import com.fse.customer.validator.ValidatorUtil;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	

	@PostMapping(value = "/register", consumes = { "application/json"})
	public ResponseEntity<?> registerCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
		if(!ValidatorUtil.isValidPhoneNumber(customerRequest.getPhoneNumber())) {
			throw new CustomConstraintException("INVALID-Phone-Number", "Invalid phone number");
		}	
			
		customerService.registerCustomer(customerRequest);
		return ResponseEntity.ok("Registration successful.");
	}
}
