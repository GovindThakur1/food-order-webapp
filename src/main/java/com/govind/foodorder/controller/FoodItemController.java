package com.govind.foodorder.controller;

import com.govind.foodorder.dto.FoodItemDto;
import com.govind.foodorder.model.FoodItem;
import com.govind.foodorder.request.AddFoodItemRequest;
import com.govind.foodorder.request.UpdateFoodItemRequest;
import com.govind.foodorder.response.ApiResponse;
import com.govind.foodorder.service.food.IFoodItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/fooditems")
public class FoodItemController {

    private final IFoodItemService foodItemService;


    @PostMapping("/restaurant/{restaurantId}")
    public ResponseEntity<ApiResponse> addFoodItem(@RequestBody AddFoodItemRequest request, @PathVariable Long restaurantId) {
        FoodItem foodItem = foodItemService.addFood(request, restaurantId);
        FoodItemDto foodItemDto = foodItemService.convertToDto(foodItem);
        return ResponseEntity.status(CREATED).body(new ApiResponse("Food item added", foodItemDto));
    }

    @GetMapping("/fooditem/{foodId}")
    public ResponseEntity<ApiResponse> getFoodItemById(@PathVariable Long foodId) {
        FoodItem foodItem = foodItemService.getFoodItemById(foodId);
        FoodItemDto foodItemDto = foodItemService.convertToDto(foodItem);
        return ResponseEntity.status(FOUND).body(new ApiResponse("Found", foodItemDto));
    }

    @GetMapping("/fooditem/all")
    public ResponseEntity<ApiResponse> getAllFoodItems() {
        List<FoodItem> allFoodItems = foodItemService.getAllFoodItems();

        if (allFoodItems.isEmpty())
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No any food item found", null));

        List<FoodItemDto> foodItemDtos = foodItemService.convertToDto(allFoodItems);
        return ResponseEntity.status(FOUND).body(new ApiResponse("Found", foodItemDtos));
    }

    @PutMapping("/fooditem/{foodId}/update")
    public ResponseEntity<ApiResponse> updateFoodItem(@RequestBody UpdateFoodItemRequest request, @PathVariable Long foodId) {
        FoodItem foodItem = foodItemService.updateFoodItem(request, foodId);
        FoodItemDto foodItemDto = foodItemService.convertToDto(foodItem);
        return ResponseEntity.ok(new ApiResponse("Updated", foodItemDto));
    }

    @DeleteMapping("/fooditem/{foodId}/delete")
    public ResponseEntity<ApiResponse> deleteFoodItem(@PathVariable Long foodId) {
        foodItemService.deleteFoodItemById(foodId);
        return ResponseEntity.ok(new ApiResponse("Deleted", null));
    }

    @GetMapping("/fooditem/by-name")
    public ResponseEntity<ApiResponse> getFoodItemByName(@RequestParam String foodName) {
        List<FoodItem> foodItems = foodItemService.getFoodItemsByName(foodName);

        if (foodItems.isEmpty())
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Food item with such name not found", null));

        List<FoodItemDto> foodItemDtos = foodItemService.convertToDto(foodItems);
        return ResponseEntity.status(FOUND).body(new ApiResponse("Found", foodItemDtos));
    }

    @GetMapping("/fooditem/by-restaurant")
    public ResponseEntity<ApiResponse> getFoodItemByRestaurant(@RequestParam String restaurantName) {
        List<FoodItem> foodItems = foodItemService.getFoodItemsByRestaurant(restaurantName);

        if (foodItems.isEmpty())
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Food item in this restaurant name not found", null));

        List<FoodItemDto> foodItemDtos = foodItemService.convertToDto(foodItems);
        return ResponseEntity.status(FOUND).body(new ApiResponse("Found", foodItemDtos));
    }

    @GetMapping("/fooditem/by-name-and-restaurant")
    public ResponseEntity<ApiResponse> getFoodItemByRestaurantAndName(@RequestParam String restaurantName,
                                                                      @RequestParam String foodName) {
        List<FoodItem> foodItems = foodItemService.getFoodItemsByRestaurantAndName(restaurantName, foodName);

        if (foodItems.isEmpty())
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Food item with this name and restaurant not found", null));

        List<FoodItemDto> foodItemDtos = foodItemService.convertToDto(foodItems);
        return ResponseEntity.status(FOUND).body(new ApiResponse("Found", foodItems));
    }

    @GetMapping("/fooditem/by-category")
    public ResponseEntity<ApiResponse> getFoodItemByCategory(@RequestParam String categoryName) {
        List<FoodItem> foodItems = foodItemService.getFoodItemsByCategory(categoryName);

        if (foodItems.isEmpty())
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Food item with this category not found", null));

        List<FoodItemDto> foodItemDtos = foodItemService.convertToDto(foodItems);
        return ResponseEntity.status(FOUND).body(new ApiResponse("Found", foodItemDtos));
    }

}
