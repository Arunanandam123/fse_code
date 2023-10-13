package com.fse.restaurantapi.command;

import java.util.List;

public class MenuUpdateCommand {
	private String restaurantName;
	private List<MenuCreateCommand> updatedMenus;
	
	public MenuUpdateCommand(String restaurantName, List<MenuCreateCommand> updatedMenus) {		
		this.restaurantName = restaurantName;
		this.updatedMenus = updatedMenus;
	}
	
	public MenuUpdateCommand() {
		super();
		// TODO Auto-generated constructor stub
	}	

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public List<MenuCreateCommand> getUpdatedMenus() {
		return updatedMenus;
	}

	public void setUpdatedMenus(List<MenuCreateCommand> updatedMenus) {
		this.updatedMenus = updatedMenus;
	}
}
