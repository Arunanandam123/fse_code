package com.fse.restaurantapi.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fse.restaurantapi.command.RestaurantCreateCommand;
import com.fse.restaurantapi.entity.MenuEntity;
import com.fse.restaurantapi.entity.RestaurantEntity;

@Component
public class RestaurantCreateCommandMapper {

	@Autowired
	private ModelMapper modelMapper;

	public RestaurantCreateCommand toDTO(RestaurantEntity restaurantEntity) {
		return modelMapper.map(restaurantEntity, RestaurantCreateCommand.class);
	}

	public RestaurantEntity toEntity(RestaurantCreateCommand restaurantCreateCommand) {
		RestaurantEntity restaurantEntity = modelMapper.map(restaurantCreateCommand, RestaurantEntity.class);

		if (restaurantCreateCommand.getMenu() != null) {
			List<MenuEntity> menuEntities = restaurantCreateCommand.getMenu().stream().map(menuItemDTO -> {
				MenuEntity menuEntity = modelMapper.map(menuItemDTO, MenuEntity.class);
				menuEntity.setRestaurant(restaurantEntity);
				return menuEntity;
			}).collect(Collectors.toList());
			restaurantEntity.setMenu(menuEntities);
		}

		return restaurantEntity;
	}
}
