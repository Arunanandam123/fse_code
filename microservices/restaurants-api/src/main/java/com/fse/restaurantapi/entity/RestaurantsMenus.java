package com.fse.restaurantapi.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "RestaurantsMenus")
public class RestaurantsMenus {

	private String restaurantName;
	private String menuItemName;
	private String restaurantAddress;
	private int restaurantRating;
	private Double menuPrice;
	
	@DynamoDBAttribute(attributeName = "RestaurantAddress")
	public String getRestaurantAddress() {
		return restaurantAddress;
	}

	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}	
	
	@DynamoDBAttribute(attributeName = "RestaurantRating")
	public int getRestaurantRating() {
		return restaurantRating;
	}

	public void setRestaurantRating(int restaurantRating) {
		this.restaurantRating = restaurantRating;
	}

	@DynamoDBAttribute(attributeName = "MenuPrice")
	public Double getMenuPrice() {
		return menuPrice;
	}

	public void setMenuPrice(Double menuPrice) {
		this.menuPrice = menuPrice;
	}

	@DynamoDBHashKey(attributeName = "RestaurantName")
	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	@DynamoDBRangeKey(attributeName = "MenuItemName")
	public String getMenuItemName() {
		return menuItemName;
	}

	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

}
