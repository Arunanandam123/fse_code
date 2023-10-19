package com.fse.restaurantapi.mapper;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.fse.restaurantapi.entity.RestaurantEntity;
import com.fse.restaurantapi.query.RestaurantQueryEvent;

class RestaurantQueryEventMapperTest {

	@InjectMocks
	@Autowired
	private RestaurantQueryEventMapper restaurantQueryEventMapper = new RestaurantQueryEventMapper();

	@Mock
	private ModelMapper modelMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

	}

	@Test
	void testToDTO() {
		// Mock data
		RestaurantEntity restaurantEntity = new RestaurantEntity();
		RestaurantQueryEvent restaurantQueryEvent = new RestaurantQueryEvent();

		// Mock the modelMapper behavior
		Mockito.when(modelMapper.map(restaurantEntity, RestaurantQueryEvent.class)).thenReturn(restaurantQueryEvent);

		// Perform the test
		RestaurantQueryEvent result = restaurantQueryEventMapper.toDTO(restaurantEntity);

		// Verify the behavior
		assertEquals(restaurantQueryEvent, result);
	}

	@Test
	void testToEntity() {
		// Mock data
		RestaurantQueryEvent restaurantQueryEvent = new RestaurantQueryEvent();
		RestaurantEntity restaurantEntity = new RestaurantEntity();

		// Mock the modelMapper behavior
		Mockito.when(modelMapper.map(restaurantQueryEvent, RestaurantEntity.class)).thenReturn(restaurantEntity);

		// Perform the test
		RestaurantEntity result = restaurantQueryEventMapper.toEntity(restaurantQueryEvent);

		// Verify the behavior
		assertEquals(restaurantEntity, result);
	}
}
