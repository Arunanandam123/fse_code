package com.fse.restaurantapi.query;

import java.util.List;

public class RestaurantQueryEvent {

	private String name;

	private String address;

	private double ratings;
	
	private double price;

	private List<MenuQueryEvent> menu;

	public RestaurantQueryEvent() {
		// Default constructor
	}

	public RestaurantQueryEvent(String name, String address, double price, double ratings, List<MenuQueryEvent> menu) {
		this.name = name;
		this.address = address;
		this.ratings = ratings;
		this.price = price;
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

	public double getRatings() {
		return ratings;
	}

	public void setRatings(double ratings) {
		this.ratings = ratings;
	}

	public List<MenuQueryEvent> getMenu() {
		return menu;
	}

	public void setMenu(List<MenuQueryEvent> menu) {
		this.menu = menu;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public static class RestaurantBuilder {
		private String name;
		private String address;
		private double ratings;
		private double price;
		private List<MenuQueryEvent> menu;
		
		public RestaurantBuilder name(String name) {
			this.name = name;
			return this;
		}

		public RestaurantBuilder address(String address) {
			this.address = address;
			return this;
		}

		public RestaurantBuilder ratings(double ratings) {
			this.ratings = ratings;
			return this;
		}

		public RestaurantBuilder menu(List<MenuQueryEvent> menu) {
			this.menu = menu;
			return this;
		}
		

		public RestaurantBuilder price(double price) {
			this.price = price;
			return this;
		}

		
		public RestaurantQueryEvent build() {
			RestaurantQueryEvent restaurant = new RestaurantQueryEvent();
			restaurant.setName(this.name);
			restaurant.setAddress(this.address);
			restaurant.setRatings(this.ratings);
			restaurant.setMenu(this.menu);
			restaurant.setPrice(this.price);
			return restaurant;
		}
	}

}
