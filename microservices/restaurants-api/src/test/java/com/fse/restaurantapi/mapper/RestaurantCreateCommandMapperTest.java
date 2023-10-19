package com.fse.restaurantapi.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.fse.restaurantapi.command.RestaurantCreateCommand;
import com.fse.restaurantapi.entity.RestaurantEntity;

class RestaurantCreateCommandMapperTest {

	@InjectMocks
	@Autowired
	private RestaurantCreateCommandMapper restaurantCreateCommandMapper = new RestaurantCreateCommandMapper();

	@Mock
	private ModelMapper modelMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testToDTO() {

		RestaurantEntity restaurantEntity = new RestaurantEntity();
		RestaurantCreateCommand restaurantCreateCommand = new RestaurantCreateCommand();

		Mockito.when(modelMapper.map(restaurantEntity, RestaurantCreateCommand.class))
				.thenReturn(restaurantCreateCommand);

		RestaurantCreateCommand result = restaurantCreateCommandMapper.toDTO(restaurantEntity);

		assertEquals(restaurantCreateCommand, result);
	}

	@Test
	void testToEntity() {

		RestaurantCreateCommand restaurantCreateCommand = new RestaurantCreateCommand();
		restaurantCreateCommand.setMenu(new ArrayList<>());
		RestaurantEntity restaurantEntity = new RestaurantEntity();

		Mockito.when(modelMapper.map(restaurantCreateCommand, RestaurantEntity.class)).thenReturn(restaurantEntity);

		RestaurantEntity result = restaurantCreateCommandMapper.toEntity(restaurantCreateCommand);

		assertEquals(restaurantEntity, result);
	}
}
