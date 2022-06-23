package com.app.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.restaurant.model.AppUser;
import com.app.restaurant.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
}
