package com.fse.restaurantapi.command;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class MenuCreateCommand {

	@NotBlank(message = "Menu name is required")
	private String name;
	
	@Min(value = 100, message = "Price must be at least 100")
    @Max(value = 200, message = "Price must be at most 200")
	private Double price;
	
	private Long restaurantId;
	
	public MenuCreateCommand() {
		super();
		// TODO Auto-generated constructor stub
	}	

	public MenuCreateCommand(String name, double price) {		
		this.name = name;
		this.price = price;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

	public Double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
}
