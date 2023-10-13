package com.fse.restaurantapi.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fse.restaurantapi.entity.RestaurantEntity;
import com.fse.restaurantapi.query.RestaurantQueryEvent;

@Component
public class RestaurantQueryEventMapper {
	@Autowired
	private ModelMapper modelMapper;

	public RestaurantQueryEvent toDTO(RestaurantEntity restaurantEntity) {
		return modelMapper.map(restaurantEntity, RestaurantQueryEvent.class);
	}

	public RestaurantEntity toEntity(RestaurantQueryEvent restaurantQueryEvent) {
		return modelMapper.map(restaurantQueryEvent, RestaurantEntity.class);
	}
}
