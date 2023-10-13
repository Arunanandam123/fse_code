package com.fse.restaurantapi.controller;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse.restaurantapi.query.CustomerRequest;
import com.fse.restaurantapi.service.RestaurantService;
import com.fse.restaurantapi.service.UserRegistrationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("food/api/v1/user")
@Validated
@CrossOrigin(origins = "*")
public class RestaurantQueryController {

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private UserRegistrationService userRegistrationService;


	@Operation(summary = "Search restaurant and menu details.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Returns search result"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Server Error") })
	@GetMapping(value = "/{criteria}/{criteriaValue}")
	public ResponseEntity<List<?>> search(@PathVariable String criteria, @PathVariable String criteriaValue) {
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
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Registration succesfull."),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Server Error") })
	@PostMapping(value = "/register")
	public ResponseEntity<?> registerCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
		userRegistrationService.registerUser(customerRequest);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Created user.");
	}

}
