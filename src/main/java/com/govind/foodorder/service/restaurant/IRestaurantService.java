package com.govind.foodorder.service.restaurant;

import com.govind.foodorder.model.Restaurant;
import com.govind.foodorder.request.AddRestaurantRequest;
import com.govind.foodorder.request.UpdateRestaurantRequest;

import java.util.List;

public interface IRestaurantService {

    Restaurant addRestaurant(AddRestaurantRequest request);

    List<Restaurant> getAllRestaurants();

    Restaurant getRestaurantById(Long id);

    Restaurant getRestaurantByAddress(String address);

    Restaurant updateRestaurant(UpdateRestaurantRequest request, Long restaurantId);

    void deleteRestaurantById(Long id);

}
