package com.govind.foodorder.controller;

import com.govind.foodorder.dto.RestaurantDto;
import com.govind.foodorder.exception.AlreadyExistsException;
import com.govind.foodorder.exception.ResourceNotFoundException;
import com.govind.foodorder.model.Restaurant;
import com.govind.foodorder.request.AddRestaurantRequest;
import com.govind.foodorder.request.UpdateRestaurantRequest;
import com.govind.foodorder.response.ApiResponse;
import com.govind.foodorder.service.restaurant.IRestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/restaurants")
public class RestaurantController {

    private final IRestaurantService restaurantService;

    @PostMapping("/restaurant/create")
    public ResponseEntity<ApiResponse> addRestaurant(@RequestBody AddRestaurantRequest request) {
        Restaurant createdRestaurant = restaurantService.addRestaurant(request);
        RestaurantDto restaurantDto = restaurantService.convertToRestaurantDto(createdRestaurant);
        return ResponseEntity.status(CREATED).body(new ApiResponse("Restaurant created", restaurantDto));
    }

    @GetMapping("/restaurant/all")
    public ResponseEntity<ApiResponse> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        List<RestaurantDto> restaurantDtos = restaurants.stream()
                .map(restaurantService::convertToRestaurantDto)
                .toList();

        return ResponseEntity.status(FOUND).body(new ApiResponse("Found", restaurantDtos));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<ApiResponse> getRestaurantById(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        RestaurantDto restaurantDto = restaurantService.convertToRestaurantDto(restaurant);
        return ResponseEntity.status(FOUND).body(new ApiResponse("Found", restaurantDto));
    }

    @GetMapping("/restaurant/by/address")
    public ResponseEntity<ApiResponse> getRestaurantByAddress(@RequestParam String address) {
        Restaurant restaurant = restaurantService.getRestaurantByAddress(address);
        RestaurantDto restaurantDto = restaurantService.convertToRestaurantDto(restaurant);
        return ResponseEntity.status(FOUND).body(new ApiResponse("Found", restaurantDto));
    }

    @PutMapping("/restaurant/{restaurantId}/update")
    public ResponseEntity<ApiResponse> updateRestaurant(@RequestBody UpdateRestaurantRequest request,
                                                        @PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.updateRestaurant(request, restaurantId);
        RestaurantDto restaurantDto = restaurantService.convertToRestaurantDto(restaurant);
        return ResponseEntity.ok(new ApiResponse("Updated", restaurantDto));
    }

    @DeleteMapping("/restaurant/{restaurantId}/delete")
    public ResponseEntity<ApiResponse> deleteRestaurant(@PathVariable Long restaurantId) {
        restaurantService.deleteRestaurantById(restaurantId);
        return ResponseEntity.ok(new ApiResponse("Restaurant deleted", null));
    }

}
