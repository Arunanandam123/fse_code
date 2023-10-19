package com.fse.restaurantapi.command;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.fse.restaurantapi.config.EventSerializerConfig;
import com.fse.restaurantapi.entity.RestaurantEntity;
import com.fse.restaurantapi.exception.RestaurantNotFoundException;
import com.fse.restaurantapi.mapper.RestaurantCreateCommandMapper;
import com.fse.restaurantapi.repository.RestaurantRepository;

public class CommandHandlerTest {

	@InjectMocks
	@Autowired
	private CommandHandler commandHandler = new CommandHandler();

	@Mock
	private RestaurantCreateCommandMapper restaurantCreateCommandMapper;

	@Mock
	private RestaurantRepository restaurantRepository;

	@Mock
	private EventSerializerConfig eventSerializer;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testHandleCreateRestaurantCommand() {

		String command = "SampleCommand";
		RestaurantCreateCommand restaurantCreateCommand = new RestaurantCreateCommand();
		RestaurantEntity restaurantEntity = new RestaurantEntity();

		Mockito.when(eventSerializer.deserializeEvent(command, RestaurantCreateCommand.class))
				.thenReturn(restaurantCreateCommand);
		Mockito.when(restaurantCreateCommandMapper.toEntity(restaurantCreateCommand)).thenReturn(restaurantEntity);

		commandHandler.handleCreateRestaurantCommand(command);

		Mockito.verify(restaurantRepository).save(restaurantEntity);
	}

//    @Test
//    void testHandleUpdateRestaurantCommand() {
//        
//        String command = "SampleCommand";
//        MenuUpdateCommand menuUpdateCommand = new MenuUpdateCommand();
//        menuUpdateCommand.setRestaurantName("SampleRestaurant");
//        RestaurantEntity restaurantEntity = new RestaurantEntity();
//
//       
//        Mockito.when(eventSerializer.deserializeEvent(command, MenuUpdateCommand.class)).thenReturn(menuUpdateCommand);
//        Mockito.when(restaurantRepository.findByName("SampleRestaurant")).thenReturn(List.of(restaurantEntity));
//
//        
//        commandHandler.handleUpdateRestaurantCommand(command);
//
//        // Verify the behavior
//        Mockito.verify(restaurantRepository).save(restaurantEntity);
//    }

	@Test
	void testHandleUpdateRestaurantCommand_InvalidRestaurantName() {

		String command = "SampleCommand";
		MenuUpdateCommand menuUpdateCommand = new MenuUpdateCommand();
		menuUpdateCommand.setRestaurantName("InvalidRestaurant");

		Mockito.when(eventSerializer.deserializeEvent(command, MenuUpdateCommand.class)).thenReturn(menuUpdateCommand);
		Mockito.when(restaurantRepository.findByName("InvalidRestaurant")).thenReturn(null);

		assertThrows(RestaurantNotFoundException.class, () -> commandHandler.handleUpdateRestaurantCommand(command));
	}
}
