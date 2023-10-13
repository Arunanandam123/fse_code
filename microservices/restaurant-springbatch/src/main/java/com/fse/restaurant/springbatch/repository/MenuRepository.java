package com.fse.restaurant.springbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fse.restaurant.springbatch.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
	
}

