package com.fse.restaurantapi.command;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fse.restaurantapi.config.EventSerializerConfig;
import com.fse.restaurantapi.entity.MenuEntity;
import com.fse.restaurantapi.entity.RestaurantEntity;
import com.fse.restaurantapi.exception.RestaurantNotFoundException;
import com.fse.restaurantapi.mapper.RestaurantCreateCommandMapper;
import com.fse.restaurantapi.repository.RestaurantRepository;

@Service
public class CommandHandler {

	private static final Logger logger = LoggerFactory.getLogger(CommandHandler.class);

	@Autowired
	RestaurantCreateCommandMapper restaurantCreateCommandMapper;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private EventSerializerConfig eventSerializer;

	@KafkaListener(topics = "create-restaurant", groupId = "create-restaurant-group")
	public void handleCreateRestaurantCommand(String command) {
		logger.debug("inside handleCreateRestaurantCommand() method.");

		RestaurantCreateCommand restaurantCreateCommand = eventSerializer.deserializeEvent(command,
				RestaurantCreateCommand.class);

		RestaurantEntity restaurantEntity = restaurantCreateCommandMapper.toEntity(restaurantCreateCommand);
		restaurantRepository.save(restaurantEntity);

		logger.debug("saved the entity in database.");

	}

	@Transactional
	@KafkaListener(topics = "update-restaurant", groupId = "update-restaurant-group")
	public void handleUpdateRestaurantCommand(String command) {
		logger.debug("inside handleUpdateRestaurantCommand() method.");

		MenuUpdateCommand menuUpdateCommand = eventSerializer.deserializeEvent(command, MenuUpdateCommand.class);

		List<RestaurantEntity> restaurantEntityList = restaurantRepository
				.findByName(menuUpdateCommand.getRestaurantName());
		if (restaurantEntityList == null || (restaurantEntityList != null && restaurantEntityList.isEmpty())) {
			throw new RestaurantNotFoundException("INVALID-RESTAURANT-NAME", "Invalid restaurant name.");
		}

		List<MenuEntity> menuEntityList = new ArrayList<>();
		// Updating the restaurants matching the name. Not in the requirement , but has to be done. 
		for (RestaurantEntity restaurantEntity : restaurantEntityList) {
			for (MenuEntity menuEntity : restaurantEntity.getMenu()) {
				for (MenuCreateCommand updatedMenu : menuUpdateCommand.getUpdatedMenus()) {
					if (menuEntity.getName().equals(updatedMenu.getName())) {
						menuEntity.setPrice(updatedMenu.getPrice());
					}
					menuEntityList.add(menuEntity);
				}
			}
			restaurantRepository.save(restaurantEntity);
		}

		logger.debug("Updated the entity in database.");

	}

}
