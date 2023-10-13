package com.fse.restaurantapi.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fse.restaurantapi.command.MenuCreateCommand;
import com.fse.restaurantapi.command.MenuUpdateCommand;
import com.fse.restaurantapi.command.RestaurantCreateCommand;
import com.fse.restaurantapi.config.EventSerializerConfig;
import com.fse.restaurantapi.exception.CustomConstraintException;
import com.fse.restaurantapi.query.QueryHandler;
import com.fse.restaurantapi.query.RestaurantQueryEvent;

@Service
public class RestaurantService {

	private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private EventSerializerConfig eventSerializer;
	
	@Autowired
	private QueryHandler queryHandler;

	public void createRestaurant(RestaurantCreateCommand restaurantCreateCommand) {
		if (logger.isDebugEnabled()) {
			logger.debug("Inside createRestaurant() method");
		}
		validateMenuItems(restaurantCreateCommand.getMenu());
		// Serialize the event and add it to the queue.
		String eventJson = eventSerializer.serializeEvent(restaurantCreateCommand);
		kafkaTemplate.send("create-restaurant", eventJson);
	}

	public void updateMenu(String restaurantName, List<MenuCreateCommand> newMenuItems) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Inside updateMenu() method");
		}
		validateMenuItems(newMenuItems);

		MenuUpdateCommand menuUpdateCommand = new MenuUpdateCommand(restaurantName,newMenuItems);
		String eventJson = eventSerializer.serializeEvent(menuUpdateCommand);
		kafkaTemplate.send("update-restaurant", eventJson);
		
	}
	
	private void validateMenuItems(List<MenuCreateCommand> menuItems) {
		if (!ValidatorUtil.isValidMenuItems(menuItems)) {
			throw new CustomConstraintException("INVALID-MENU-ITEM", "Create menu only from predefined list.");
		}
		ValidatorUtil.validateMenuPrice(menuItems);

	}

	public List<RestaurantQueryEvent> queryRestaurantsByName(String name) {
		return queryHandler.queryRestaurantsByName(name);		
	}

	public List<RestaurantQueryEvent> queryRestaurantsByMenusName(String name) {
		return queryHandler.queryRestaurantsByMenusName(name);
	}


}
