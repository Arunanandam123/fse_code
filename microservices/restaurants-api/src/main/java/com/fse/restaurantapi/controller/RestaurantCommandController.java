package com.fse.restaurantapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse.restaurantapi.command.MenuCreateCommand;
import com.fse.restaurantapi.command.RestaurantCreateCommand;
import com.fse.restaurantapi.service.RestaurantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("food/api/v1/admin")
@ConditionalOnProperty(name = "app.write.enabled", havingValue = "true")
@Validated
public class RestaurantCommandController {
	
	@Autowired
	private RestaurantService restaurantService;

	@Operation(summary = "Add a new restaurant detail.")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Accepted Restauramt add request."),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Server Error") })
	@PostMapping(value = "/add-restaurant", consumes = { "application/json" })
	@SecurityRequirement(name = "Bearer Authentication")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> addRestaurant(@Valid @RequestBody RestaurantCreateCommand restaurantCreateCommand) {
		restaurantService.createRestaurant(restaurantCreateCommand);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Request to create a new restaurant accepted.");
	}
	
	@Operation(summary = "Update menu/price for a given restaurant.")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Accepted Menu update request."),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "404", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Server Error")})
	@PutMapping(value = "/update-price/menu/{restaurantName}", consumes = { "application/json" })
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> updateMenu( @PathVariable String restaurantName,
            @RequestBody List<MenuCreateCommand> newMenu) {
		restaurantService.updateMenu(restaurantName,newMenu);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Request to update the price of the menus accepted.");
	}
}
