package com.fse.restaurantapi.controller;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse.restaurantapi.query.AuthResponse;
import com.fse.restaurantapi.query.CustomerRequest;
import com.fse.restaurantapi.query.LoginRequest;
import com.fse.restaurantapi.service.RestaurantService;
import com.fse.restaurantapi.service.UserRegistrationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("food/api/v1/user")
@Validated
@ConditionalOnProperty(name = "app.write.enabled", havingValue = "false")
@CrossOrigin("http://localhost:4200")
public class RestaurantQueryController {

	private static final Logger logger = LoggerFactory.getLogger(RestaurantQueryController.class);

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private UserRegistrationService userRegistrationService;


	@Operation(summary = "Search restaurant and menu details.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Returns search result"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Server Error") })
	@SecurityRequirement(name = "Bearer Authentication")
	@GetMapping(value = "/search/{criteria}/{criteriaValue}")
	//@PreAuthorize("hasRole('ROLE_USER')")
	@CrossOrigin(allowedHeaders = {"Authorization", "Origin"})
	public ResponseEntity<List<?>> search(@PathVariable String criteria, @PathVariable String criteriaValue) {
		logger.info("Search requested initaited for {} with value {}",criteria,criteriaValue);
		if ("restaurant".equalsIgnoreCase(criteria)) {
			return ResponseEntity.status(HttpStatus.OK).body(restaurantService.queryRestaurantsByName(criteriaValue));
		} else if ("menu".equalsIgnoreCase(criteria)) {
			
			return ResponseEntity.status(HttpStatus.OK)
					.body(restaurantService.queryRestaurantsByMenusName(criteriaValue));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
		}
	}
	
	@Operation(summary = "Register customer.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Registration successfull."),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Server Error") })
	@PostMapping(value = "/register")
	public ResponseEntity<?> registerCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
		logger.info("Registering user with user name {}",customerRequest.getName());
		userRegistrationService.registerUser(customerRequest);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Created user.");
	}
	
	@Operation(summary = "Customer Login.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Login successfull."),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Server Error") })
	@PostMapping(value = "/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
		AuthResponse authResponse = userRegistrationService.loginUser(loginRequest);
		return ResponseEntity.status(HttpStatus.OK).body(authResponse);
	}

}
