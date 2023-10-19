package com.fse.restaurantapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fse.restaurantapi.command.MenuCreateCommand;
import com.fse.restaurantapi.command.RestaurantCreateCommand;
import com.fse.restaurantapi.service.RestaurantService;

public class RestaurantCommandControllerTest {

	@InjectMocks
    @Autowired
    private RestaurantCommandController restaurantCommandController;

    @Mock
    private RestaurantService restaurantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        }

    @Test
    public void testAddRestaurant() {
        RestaurantCreateCommand restaurantCreateCommand = new RestaurantCreateCommand();

        Mockito.doNothing().when(restaurantService).createRestaurant(restaurantCreateCommand);
        
        ResponseEntity<String> response = restaurantCommandController.addRestaurant(restaurantCreateCommand);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("Request to create a new restaurant accepted.", response.getBody());
    }

    @Test
    public void testUpdateMenu() {
        String restaurantName = "RestaurantName";
        List<MenuCreateCommand> newMenu = Arrays.asList(
                new MenuCreateCommand("PIZZA", 150.00),
                new MenuCreateCommand("BURGER", 120.00)
        );

        Mockito.doNothing().when(restaurantService).updateMenu(restaurantName, newMenu);

        Authentication auth = new UsernamePasswordAuthenticationToken("admin", null, Arrays.asList(() -> "ROLE_ADMIN"));
        SecurityContextHolder.getContext().setAuthentication(auth);

        ResponseEntity<String> response = restaurantCommandController.updateMenu(restaurantName, newMenu);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("Request to update the price of the menus accepted.", response.getBody());
    }
}

