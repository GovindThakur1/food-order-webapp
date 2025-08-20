package com.govind.foodorder.service.food;

import com.govind.foodorder.dto.FoodItemDto;
import com.govind.foodorder.model.FoodItem;
import com.govind.foodorder.request.AddFoodItemRequest;
import com.govind.foodorder.request.UpdateFoodItemRequest;

import java.util.List;

public interface IFoodItemService {

    FoodItem addFood(AddFoodItemRequest request, Long restaurantId);

    FoodItem getFoodItemById(Long id);

    FoodItem updateFoodItem(UpdateFoodItemRequest request, Long foodId);

    void deleteFoodItemById(Long id);

    List<FoodItem> getAllFoodItems();

    List<FoodItem> getFoodItemsByName(String foodName);

    List<FoodItem> getFoodItemsByRestaurant(String restaurantName);

    List<FoodItem> getFoodItemsByRestaurantAndName(String restaurantName, String foodName);

    List<FoodItem> getFoodItemsByCategory(String categoryName);

    FoodItemDto convertToDto(FoodItem foodItem);

    List<FoodItemDto> convertToDto(List<FoodItem> foodItems);

}
