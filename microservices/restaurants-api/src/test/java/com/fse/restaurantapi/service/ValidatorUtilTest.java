package com.fse.restaurantapi.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fse.restaurantapi.command.MenuCreateCommand;
import com.fse.restaurantapi.exception.CustomConstraintException;

public class ValidatorUtilTest {

    @Test
    public void testValidateMenuPrice_ValidPrices() {
        List<MenuCreateCommand> validMenus = Arrays.asList(
                new MenuCreateCommand("PIZZA", 150.00),
                new MenuCreateCommand("BURGER", 120.00)
        );

        assertDoesNotThrow(() -> ValidatorUtil.validateMenuPrice(validMenus));
    }

    @Test
    public void testValidateMenuPrice_InvalidPrices() {
        List<MenuCreateCommand> invalidMenus = Arrays.asList(
                new MenuCreateCommand("PIZZA", 50.00),
                new MenuCreateCommand("BURGER", 250.00)
        );

        CustomConstraintException exception = assertThrows(CustomConstraintException.class,
                () -> ValidatorUtil.validateMenuPrice(invalidMenus));

        assertEquals("INVALID-PRICE-RANGE", exception.getExceptionCode());
        assertEquals("Price must be between 100 and 200.", exception.getMessage());
    }

    @Test
    public void testIsValidMenuItems_ValidItems() {
        List<MenuCreateCommand> validItems = Arrays.asList(
                new MenuCreateCommand("PIZZA", 150.00),
                new MenuCreateCommand("BURGER", 120.00)
        );

        assertTrue(ValidatorUtil.isValidMenuItems(validItems));
    }

    @Test
    public void testIsValidMenuItems_InvalidItems() {
        List<MenuCreateCommand> invalidItems = Arrays.asList(
                new MenuCreateCommand("PIZZA", 150.00),
                new MenuCreateCommand("SUSHI", 120.00)
        );

        assertFalse(ValidatorUtil.isValidMenuItems(invalidItems));
    }
}

