package com.fse.restaurant.springbatch.config;

public class RestaurantMenuDTO {
	private String restaurantName;
	private double restaurantRating;
	private String restaurantAddress;
	private String menuName;
	private double menuPrice;

	public RestaurantMenuDTO() {
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public double getRestaurantRating() {
		return restaurantRating;
	}

	public void setRestaurantRating(double restaurantRating) {
		this.restaurantRating = restaurantRating;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public double getMenuPrice() {
		return menuPrice;
	}

	public void setMenuPrice(double menuPrice) {
		this.menuPrice = menuPrice;
	}

	public String getRestaurantAddress() {
		return restaurantAddress;
	}

	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}
}
