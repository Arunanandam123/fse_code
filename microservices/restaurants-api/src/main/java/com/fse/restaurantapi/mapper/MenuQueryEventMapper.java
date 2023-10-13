package com.fse.restaurantapi.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fse.restaurantapi.entity.MenuEntity;
import com.fse.restaurantapi.query.MenuQueryEvent;

@Component
public class MenuQueryEventMapper {
	@Autowired
	private ModelMapper modelMapper;

	public MenuQueryEvent toDTO(MenuEntity menuEntity) {
		return modelMapper.map(menuEntity, MenuQueryEvent.class);
	}

	public MenuEntity toEntity(MenuQueryEvent menuQueryEvent) {
		return modelMapper.map(menuQueryEvent, MenuEntity.class);
	}

}
