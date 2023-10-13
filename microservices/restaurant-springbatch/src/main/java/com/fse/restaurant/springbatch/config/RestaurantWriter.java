package com.fse.restaurant.springbatch.config;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.fse.restaurant.springbatch.entity.Restaurant;
import com.fse.restaurant.springbatch.repository.RestaurantRepository;

public class RestaurantWriter implements ItemWriter<Restaurant> {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Override
	public void write(List<? extends Restaurant> restaurants) throws Exception {
		for (Restaurant restaurant : restaurants) {
			restaurantRepository.save(restaurant);
		}
	}
}
