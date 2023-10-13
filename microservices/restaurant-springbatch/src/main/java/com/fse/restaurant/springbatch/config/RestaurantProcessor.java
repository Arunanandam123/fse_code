package com.fse.restaurant.springbatch.config;

import org.springframework.batch.item.ItemProcessor;

import com.fse.restaurant.springbatch.entity.Menu;
import com.fse.restaurant.springbatch.entity.Restaurant;

public class RestaurantProcessor implements ItemProcessor<RestaurantMenuDTO, Restaurant> {

	@Override
	public Restaurant process(RestaurantMenuDTO restaurantMenuDTO) throws Exception {
		Restaurant restaurant = new Restaurant();
		restaurant.setName(restaurantMenuDTO.getRestaurantName());
		restaurant.setAddress(restaurantMenuDTO.getRestaurantAddress());

		restaurant.setRatings(restaurantMenuDTO.getRestaurantRating());

		Menu menu = new Menu();
		menu.setName(restaurantMenuDTO.getMenuName());
		menu.setPrice(restaurantMenuDTO.getMenuPrice());
		menu.setRestaurant(restaurant);

		restaurant.addMenuItem(menu);
		return restaurant;
	}
}
