package com.fse.restaurantapi.service;

import java.util.Arrays;
import java.util.List;

import com.fse.restaurantapi.command.MenuCreateCommand;
import com.fse.restaurantapi.exception.CustomConstraintException;

public class ValidatorUtil {

	private static String[] staticMenuArray = { "NAAN", "PIZZA", "BURGER", "FRIES", "DOSA" };

	public static void validateMenuPrice(List<MenuCreateCommand> menus) {
		for (MenuCreateCommand menu : menus) {
			if (menu.getPrice() == null || menu.getPrice() < 100.00 || menu.getPrice() > 200.00) {
				throw new CustomConstraintException("INVALID-PRICE-RANGE", "Price must be between 100 and 200.");
			}
		}
	}

	public static boolean isValidMenuItems(List<MenuCreateCommand> menuItems) {
		List<String> staticMenuList = Arrays.asList(staticMenuArray);
		return menuItems.stream().map(MenuCreateCommand::getName).allMatch(menuName -> staticMenuList.contains(menuName));

	}

}
