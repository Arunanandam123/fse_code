package com.fse.restaurantapi.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.fse.restaurantapi.command.MenuCreateCommand;
import com.fse.restaurantapi.command.MenuUpdateCommand;
import com.fse.restaurantapi.command.RestaurantCreateCommand;
import com.fse.restaurantapi.config.EventSerializerConfig;
import com.fse.restaurantapi.exception.CustomConstraintException;
import com.fse.restaurantapi.query.QueryHandler;

public class RestaurantServiceTests {

	@Mock
    KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    EventSerializerConfig eventSerializer;

    @Mock
    QueryHandler queryHandler;

    @InjectMocks
    @Autowired
    RestaurantService restaurantService = new RestaurantService();
    
    @BeforeEach
    public void setUp() throws Exception {       
        MockitoAnnotations.openMocks(this);        
    }

    @Test
    public void testCreateRestaurant_ValidMenu() {
        RestaurantCreateCommand restaurantCreateCommand = new RestaurantCreateCommand();
        restaurantCreateCommand.setMenu(Arrays.asList(
                new MenuCreateCommand("PIZZA", 150.00),
                new MenuCreateCommand("BURGER", 120.00)
        ));

        Mockito.when(eventSerializer.serializeEvent(Mockito.any())).thenReturn("serializedEvent");

        assertDoesNotThrow(() -> restaurantService.createRestaurant(restaurantCreateCommand));

        Mockito.verify(kafkaTemplate).send("create-restaurant", "serializedEvent");
    }

    @Test
    public void testCreateRestaurant_InvalidMenu() {
        RestaurantCreateCommand restaurantCreateCommand = new RestaurantCreateCommand();
        restaurantCreateCommand.setMenu(Arrays.asList(
                new MenuCreateCommand("PIZZA", 50.00),
                new MenuCreateCommand("SUSHI", 250.00)
        ));

        CustomConstraintException exception = assertThrows(CustomConstraintException.class,
                () -> restaurantService.createRestaurant(restaurantCreateCommand));

        assertEquals("INVALID-MENU-ITEM", exception.getExceptionCode());
        assertEquals("Create menu only from predefined list.", exception.getMessage());

        Mockito.verifyNoInteractions(kafkaTemplate);
    }

    @Test
    public void testUpdateMenu_ValidMenu() {
        String restaurantName = "RestaurantName";
        List<MenuCreateCommand> newMenuItems = Arrays.asList(
                new MenuCreateCommand("PIZZA", 150.00),
                new MenuCreateCommand("BURGER", 120.00)
        );

        MenuUpdateCommand expectedMenuUpdateCommand = new MenuUpdateCommand(restaurantName, newMenuItems);

        Mockito.when(eventSerializer.serializeEvent(Mockito.any())).thenReturn("serializedEvent");

        assertDoesNotThrow(() -> restaurantService.updateMenu(restaurantName, newMenuItems));

        Mockito.verify(kafkaTemplate).send("update-restaurant", "serializedEvent");
    }

    @Test
    public void testUpdateMenu_InvalidMenu() {
        String restaurantName = "RestaurantName";
        List<MenuCreateCommand> newMenuItems = Arrays.asList(
                new MenuCreateCommand("Pizza", 50.00),
                new MenuCreateCommand("Sushi", 250.00)
        );

        CustomConstraintException exception = assertThrows(CustomConstraintException.class,
                () -> restaurantService.updateMenu(restaurantName, newMenuItems));

        assertEquals("INVALID-MENU-ITEM", exception.getExceptionCode());
        assertEquals("Create menu only from predefined list.", exception.getMessage());

        Mockito.verifyNoInteractions(kafkaTemplate);
    }
}

   

