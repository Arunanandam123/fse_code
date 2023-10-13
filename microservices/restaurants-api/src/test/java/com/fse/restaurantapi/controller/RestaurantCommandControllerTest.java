package com.fse.restaurantapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.restaurantapi.service.RestaurantService;

public class RestaurantCommandControllerTest {

	@InjectMocks
	private RestaurantCommandController restaurantController;

	@Mock
	private RestaurantService restaurantService;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
    public void testAddRestaurant() throws Exception {
        
    }
}
