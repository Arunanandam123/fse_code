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

import com.fse.restaurantapi.entity.MenuEntity;
import com.fse.restaurantapi.query.MenuQueryEvent;

class MenuQueryEventMapperTest {

	@InjectMocks
	@Autowired
	private MenuQueryEventMapper menuQueryEventMapper = new MenuQueryEventMapper();

	@Mock
	private ModelMapper modelMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testToDTO() {

		MenuEntity menuEntity = new MenuEntity();
		MenuQueryEvent menuQueryEvent = new MenuQueryEvent();

		Mockito.when(modelMapper.map(menuEntity, MenuQueryEvent.class)).thenReturn(menuQueryEvent);

		MenuQueryEvent result = menuQueryEventMapper.toDTO(menuEntity);

		assertEquals(menuQueryEvent, result);
	}

	@Test
	void testToEntity() {

		MenuQueryEvent menuQueryEvent = new MenuQueryEvent();
		MenuEntity menuEntity = new MenuEntity();

		Mockito.when(modelMapper.map(menuQueryEvent, MenuEntity.class)).thenReturn(menuEntity);

		MenuEntity result = menuQueryEventMapper.toEntity(menuQueryEvent);

		assertEquals(menuEntity, result);
	}
}
