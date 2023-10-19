package com.fse.restaurantapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fse.restaurantapi.query.CustomerRequest;
import com.fse.restaurantapi.query.LoginRequest;
import com.fse.restaurantapi.query.RestaurantQueryEvent;
import com.fse.restaurantapi.service.RestaurantService;
import com.fse.restaurantapi.service.UserRegistrationService;

public class RestaurantQueryControllerTest {

	@InjectMocks
    @Autowired
	private RestaurantQueryController restaurantQueryController;

	@Mock
	private RestaurantService restaurantService;

	@Mock
	private UserRegistrationService userRegistrationService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSearch_RestaurantCriteria() {
		String criteria = "restaurant";
		String criteriaValue = "RestaurantName";

		List<?> expectedResponse = Collections.singletonList("Restaurant Details");

		Mockito.when(restaurantService.queryRestaurantsByName(criteriaValue)).thenReturn((List<RestaurantQueryEvent>) expectedResponse);

		ResponseEntity<List<?>> response = restaurantQueryController.search(criteria, criteriaValue);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedResponse, response.getBody());
	}

	@Test
	public void testSearch_MenuCriteria() {
		String criteria = "menu";
		String criteriaValue = "MenuItemName";

		List<?> expectedResponse = Collections.singletonList("Menu Details");

		Mockito.when(restaurantService.queryRestaurantsByMenusName(criteriaValue)).thenReturn((List<RestaurantQueryEvent>) expectedResponse);

		ResponseEntity<List<?>> response = restaurantQueryController.search(criteria, criteriaValue);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedResponse, response.getBody());
	}

	@Test
	public void testSearch_InvalidCriteria() {
		String criteria = "invalid";
		String criteriaValue = "InvalidValue";

		ResponseEntity<List<?>> response = restaurantQueryController.search(criteria, criteriaValue);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(Collections.emptyList(), response.getBody());
	}

	@Test
	public void testRegisterCustomer() {
		CustomerRequest customerRequest = new CustomerRequest();

		Mockito.doNothing().when(userRegistrationService).registerUser(customerRequest);

		ResponseEntity<?> response = restaurantQueryController.registerCustomer(customerRequest);

		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertEquals("Created user.", response.getBody());
	}

	@Test
	public void testLogin() {
		LoginRequest loginRequest = new LoginRequest();
		String token = "Bearer Token";

		Mockito.when(userRegistrationService.loginUser(loginRequest)).thenReturn(token);

		ResponseEntity<String> response = restaurantQueryController.login(loginRequest);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(token, response.getBody());
	}
}
