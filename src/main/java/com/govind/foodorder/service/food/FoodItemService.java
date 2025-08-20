package com.govind.foodorder.service.food;

import com.govind.foodorder.dto.FoodItemDto;
import com.govind.foodorder.exception.AlreadyExistsException;
import com.govind.foodorder.exception.ResourceNotFoundException;
import com.govind.foodorder.model.Category;
import com.govind.foodorder.model.FoodItem;
import com.govind.foodorder.model.Restaurant;
import com.govind.foodorder.repository.CategoryRepository;
import com.govind.foodorder.repository.FoodItemRepository;
import com.govind.foodorder.request.AddFoodItemRequest;
import com.govind.foodorder.request.UpdateFoodItemRequest;
import com.govind.foodorder.service.restaurant.IRestaurantService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodItemService implements IFoodItemService {

    private final FoodItemRepository foodRepository;
    private final CategoryRepository categoryRepository;
    private final IRestaurantService restaurantService;
    private final ModelMapper modelMapper;


    @Override
    public FoodItem addFood(AddFoodItemRequest request, Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (productExists(request.getName(), restaurant.getName())) {
            throw new AlreadyExistsException(request.getName() + " food already exists in the restaurant " +
                    restaurant.getName() + ". Instead update the inventory of this product.");
        }

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCat = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCat);
                });
        request.setCategory(category);

        return foodRepository.save(createFoodItem(request, category, restaurant));
    }

    private FoodItem createFoodItem(AddFoodItemRequest request, Category category, Restaurant restaurant) {
        return new FoodItem(
                request.getName(),
                request.getPrice(),
                request.getDescription(),
                restaurant,
                category
        );
    }

    private boolean productExists(String foodName, String restaurantName) {
        return foodRepository.existsByNameAndRestaurantName(foodName, restaurantName);
    }


    @Override
    public FoodItem getFoodItemById(Long id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food item not found"));
    }

    @Override
    public FoodItem updateFoodItem(UpdateFoodItemRequest request, Long foodId) {
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return foodRepository.findById(foodId)
                .map(foodItem -> {
                    if (request.getName() != null)
                        foodItem.setName(request.getName());
                    if (request.getPrice() != null)
                        foodItem.setPrice(request.getPrice());
                    if (request.getDescription() != null)
                        foodItem.setDescription(request.getDescription());
                    if (request.getCategory().getName() != null)
                        foodItem.setCategory(category);
                    return foodRepository.save(foodItem);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Food item not found"));
    }

    @Override
    public void deleteFoodItemById(Long id) {
        foodRepository.findById(id)
                .ifPresentOrElse(foodRepository::delete, () -> {
                    throw new ResourceNotFoundException("Food item not found");
                });
    }

    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodRepository.findAll();
    }

    @Override
    public List<FoodItem> getFoodItemsByName(String foodName) {
        return foodRepository.findByName(foodName);
    }

    @Override
    public List<FoodItem> getFoodItemsByRestaurant(String restaurantName) {
        return foodRepository.findByRestaurantName(restaurantName);
    }

    @Override
    public List<FoodItem> getFoodItemsByRestaurantAndName(String restaurantName, String foodName) {
        return foodRepository.findByRestaurantNameAndName(restaurantName, foodName);
    }

    @Override
    public List<FoodItem> getFoodItemsByCategory(String categoryName) {
        return foodRepository.findByCategoryName(categoryName);
    }

    @Override
    public FoodItemDto convertToDto(FoodItem foodItem) {
        return modelMapper.map(foodItem, FoodItemDto.class);
    }

    @Override
    public List<FoodItemDto> convertToDto(List<FoodItem> foodItems) {
        return foodItems.stream().map(this::convertToDto).toList();
    }
}
