package com.fse.restaurantapi.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.fse.restaurantapi.config.DynamoDBConfig;
import com.fse.restaurantapi.entity.MenuEntity;
import com.fse.restaurantapi.entity.RestaurantEntity;
import com.fse.restaurantapi.exception.MenuNotFoundException;
import com.fse.restaurantapi.exception.RestaurantNotFoundException;
import com.fse.restaurantapi.mapper.MenuQueryEventMapper;
import com.fse.restaurantapi.mapper.RestaurantQueryEventMapper;
import com.fse.restaurantapi.repository.MenuRepository;
import com.fse.restaurantapi.repository.RestaurantRepository;

@Component
public class QueryHandler {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	RestaurantQueryEventMapper restaurantQueryEventMapper;

	@Autowired
	MenuQueryEventMapper menuQueryEventMapper;
	
	@Autowired
	DynamoDBConfig dynamoDBConfig;

	private String tableName = "RestaurantsMenus";
	public List<RestaurantQueryEvent> queryRestaurantsByName(String name) {		
		
		List<RestaurantEntity> restaurantEntityList = restaurantRepository.findByName(name);
		QueryResult queryResult = performSearch("RestaurantNameIndex", "RestaurantName", name);
		
		if (restaurantEntityList != null && !restaurantEntityList.isEmpty()) {
			List<RestaurantQueryEvent> restaurantQueryEventList = new ArrayList<>();
			for (RestaurantEntity restaurantEntity : restaurantEntityList) {
				restaurantQueryEventList.add(restaurantQueryEventMapper.toDTO(restaurantEntity));
			}
			return restaurantQueryEventList;
		} else {
			throw new RestaurantNotFoundException("INVALID-RESTAURANT-NAME", "Invalid restaurant name.");
		}
	}

	public List<RestaurantQueryEvent> queryRestaurantsByMenusName(String name) {
		List<MenuEntity> menuEntityList = menuRepository.findByName(name);
		
		QueryResult queryResult =performSearch("MenuItemNameIndex", "MenuItemName", name);		
		
		List<RestaurantQueryEvent> restaurantQueryEventList = new ArrayList<>();
		if (menuEntityList != null && !menuEntityList.isEmpty()) {
			for (MenuEntity menuEntity : menuEntityList) {
				RestaurantQueryEvent restaurantQueryEvent = new RestaurantQueryEvent.RestaurantBuilder()
						.name(menuEntity.getRestaurant().getName()).address(menuEntity.getRestaurant().getAddress())
						.ratings(menuEntity.getRestaurant().getRatings())
						.price(menuEntity.getPrice())
						.menu(new ArrayList<>()).build();
				restaurantQueryEventList.add(restaurantQueryEvent);
			}
		} else {
			throw new MenuNotFoundException("INVALID-MENU-NAME", "Invalid menu provided.");
		}
		return restaurantQueryEventList;
	}

	private QueryResult performSearch(String indexName, String attributeName, String attributeValue) {
		
		Map<String, AttributeValue> attributeValues = new HashMap<>();
        attributeValues.put(":value", new AttributeValue().withS(attributeValue));

        QueryRequest queryRequest = new QueryRequest()
            .withTableName(tableName)
            .withIndexName(indexName)
            .withKeyConditionExpression("#attr = :value")
            .withExpressionAttributeNames(Collections.singletonMap("#attr", attributeName))
            .withExpressionAttributeValues(attributeValues);

        // Execute the query
        QueryResult queryResponse = dynamoDBConfig.amazonDynamoDB().query(queryRequest);

        // Process the query results
        queryResponse.getItems().forEach(item -> {            
            System.out.println(item);
        });
        
        return queryResponse;
	}

}
