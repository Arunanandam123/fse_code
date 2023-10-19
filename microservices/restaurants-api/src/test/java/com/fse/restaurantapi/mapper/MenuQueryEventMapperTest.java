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
        // Mock data
        MenuEntity menuEntity = new MenuEntity();
        MenuQueryEvent menuQueryEvent = new MenuQueryEvent();

        // Mock the modelMapper behavior
        Mockito.when(modelMapper.map(menuEntity, MenuQueryEvent.class)).thenReturn(menuQueryEvent);

        // Perform the test
        MenuQueryEvent result = menuQueryEventMapper.toDTO(menuEntity);

        // Verify the behavior
        assertEquals(menuQueryEvent, result);
    }

    @Test
    void testToEntity() {
        // Mock data
        MenuQueryEvent menuQueryEvent = new MenuQueryEvent();
        MenuEntity menuEntity = new MenuEntity();

        // Mock the modelMapper behavior
        Mockito.when(modelMapper.map(menuQueryEvent, MenuEntity.class)).thenReturn(menuEntity);

        // Perform the test
        MenuEntity result = menuQueryEventMapper.toEntity(menuQueryEvent);

        // Verify the behavior
        assertEquals(menuEntity, result);
    }
}

