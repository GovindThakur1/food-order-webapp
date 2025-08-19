package com.govind.foodorder.controller;

import com.govind.foodorder.exception.AlreadyExistsException;
import com.govind.foodorder.exception.ResourceNotFoundException;
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
        try {
            FoodItem foodItem = foodItemService.addFood(request, restaurantId);
            return ResponseEntity.status(CREATED).body(new ApiResponse("Food item added", foodItem));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/fooditem/{foodId}")
    public ResponseEntity<ApiResponse> getFoodItemById(@PathVariable Long foodId) {
        try {
            FoodItem foodItem = foodItemService.getFoodItemById(foodId);
            return ResponseEntity.status(FOUND).body(new ApiResponse("Found", foodItem));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/fooditem/all")
    public ResponseEntity<ApiResponse> getAllFoodItems() {
        try {
            List<FoodItem> allFoodItems = foodItemService.getAllFoodItems();
            if (allFoodItems.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No any food item found", null));
            return ResponseEntity.status(FOUND).body(new ApiResponse("Found", allFoodItems));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/fooditem/{foodId}/update")
    public ResponseEntity<ApiResponse> updateFoodItem(@RequestBody UpdateFoodItemRequest request, @PathVariable Long foodId) {
        try {
            FoodItem foodItem = foodItemService.updateFoodItem(request, foodId);
            return ResponseEntity.ok(new ApiResponse("Updated", foodItem));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/fooditem/{foodId}/delete")
    public ResponseEntity<ApiResponse> deleteFoodItem(@PathVariable Long foodId) {
        try {
            foodItemService.deleteFoodItemById(foodId);
            return ResponseEntity.ok(new ApiResponse("Deleted", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/fooditem/by-name")
    public ResponseEntity<ApiResponse> getFoodItemByName(@RequestParam String foodName) {
        try {
            List<FoodItem> foodItems = foodItemService.getFoodItemsByName(foodName);

            if (foodItems.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Food item with such name not found", null));

            return ResponseEntity.status(FOUND).body(new ApiResponse("Found", foodItems));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/fooditem/by-restaurant")
    public ResponseEntity<ApiResponse> getFoodItemByRestaurant(@RequestParam String restaurantName) {
        try {
            List<FoodItem> foodItems = foodItemService.getFoodItemsByRestaurant(restaurantName);

            if (foodItems.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Food item in this restaurant name not found", null));

            return ResponseEntity.status(FOUND).body(new ApiResponse("Found", foodItems));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/fooditem/by-name-and-restaurant")
    public ResponseEntity<ApiResponse> getFoodItemByRestaurantAndName(
            @RequestParam String restaurantName,
            @RequestParam String foodName) {
        try {
            List<FoodItem> foodItems = foodItemService.getFoodItemsByRestaurantAndName(restaurantName, foodName);

            if (foodItems.isEmpty())
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("Food item with this name and restaurant not found", null));

            return ResponseEntity.status(FOUND).body(new ApiResponse("Found", foodItems));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/fooditem/by-category")
    public ResponseEntity<ApiResponse> getFoodItemByCategory(@RequestParam String categoryName) {
        try {
            List<FoodItem> foodItems = foodItemService.getFoodItemsByCategory(categoryName);

            if (foodItems.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Food item with this category not found", null));

            return ResponseEntity.status(FOUND).body(new ApiResponse("Found", foodItems));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
