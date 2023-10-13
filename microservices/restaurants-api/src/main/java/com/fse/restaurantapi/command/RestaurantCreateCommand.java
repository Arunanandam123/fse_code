package com.fse.restaurantapi.command;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RestaurantCreateCommand {
	@NotBlank(message = "Restaurant name is required")
	private String name;
	
	@NotBlank(message = "Restaurant Address is required")
	private String address;
	
	@Min(value = 1, message = "Restaurant ratings must be at least 1")
    @Max(value = 10, message = "Restaurant ratings must be at most 10")
	private int ratings;
	
	@Valid
    @NotNull(message = "Menu is required")
    @Size(min = 1, message = "Menu must have at least one item")
	private List<MenuCreateCommand> menu;
	
	private String type;

	public RestaurantCreateCommand() {
		// Default constructor
	}

	public RestaurantCreateCommand(String name, String address, int ratings, List<MenuCreateCommand> menu) {
		this.name = name;
		this.address = address;
		this.ratings = ratings;
		this.menu = menu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRatings() {
		return ratings;
	}

	public void setRatings(int ratings) {
		this.ratings = ratings;
	}

	public List<MenuCreateCommand> getMenu() {
		return menu;
	}

	public void setMenu(List<MenuCreateCommand> menu) {
		this.menu = menu;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
