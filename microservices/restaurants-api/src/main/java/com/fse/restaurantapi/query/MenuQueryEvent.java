package com.fse.restaurantapi.query;

public class MenuQueryEvent {

	private String name;
	
    private double price;	

	public MenuQueryEvent() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
