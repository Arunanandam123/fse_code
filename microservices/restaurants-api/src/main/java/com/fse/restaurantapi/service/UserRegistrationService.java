package com.fse.restaurantapi.service;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fse.restaurantapi.query.CustomerRequest;

@Service
public class UserRegistrationService {

	private static final Logger logger = LoggerFactory.getLogger(UserRegistrationService.class);

	@Autowired
	RestTemplate restTemplate;

	

	public void registerUser(@Valid CustomerRequest customerRequest) {
		if (logger.isDebugEnabled()) {
			logger.debug("Inside registerUser() method");
		}

		String response = restTemplate.postForEntity("http://customer-registration-api/api/customers/register", customerRequest,String.class).getBody();
		System.err.println(response);
	}

}
