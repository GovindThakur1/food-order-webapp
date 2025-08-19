package com.govind.foodorder.repository;

import com.govind.foodorder.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    List<FoodItem> findByRestaurantNameAndName(String restaurantName, String name);

    List<FoodItem> findByCategoryName(String categoryName);

    List<FoodItem> findByRestaurantName(String restaurantName);

    List<FoodItem> findByName(String foodName);

    boolean existsByNameAndRestaurantName(String foodName, String restaurantName);
}
