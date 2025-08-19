package com.govind.foodorder.service.restaurant;

import com.govind.foodorder.exception.AlreadyExistsException;
import com.govind.foodorder.exception.ResourceNotFound;
import com.govind.foodorder.model.Restaurant;
import com.govind.foodorder.repository.RestaurantRepository;
import com.govind.foodorder.request.AddRestaurantRequest;
import com.govind.foodorder.request.UpdateRestaurantRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService implements IRestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Restaurant addRestaurant(AddRestaurantRequest request) {
        return Optional.ofNullable(request)
                .map(AddRestaurantRequest::getName)
                .filter(name -> !restaurantRepository.existsByName(name))
                .map(name -> {
                    Restaurant restaurant = new Restaurant();
                    restaurant.setName(name);
                    restaurant.setAddress(request.getAddress());
                    restaurant.setTagline(request.getTagline());
                    return restaurantRepository.save(restaurant);
                })
                .orElseThrow(() -> new AlreadyExistsException("Restaurant with name " + request.getName() + " already exists"));
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Restaurant not found"));
    }

    @Override
    public Restaurant getRestaurantByAddress(String address) {
        return Optional.ofNullable(restaurantRepository.findByAddress(address))
                .orElseThrow(() -> new ResourceNotFound("Restaurant not found"));

    }

    @Override
    public Restaurant updateRestaurant(UpdateRestaurantRequest request, Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .map(restaurant -> {
                    if (request.getName() != null)
                        restaurant.setName(request.getName());
                    if (request.getAddress() != null)
                        restaurant.setAddress(request.getAddress());
                    if (request.getTagline() != null)
                        restaurant.setTagline(request.getTagline());
                    return restaurantRepository.save(restaurant);
                })
                .orElseThrow(() -> new ResourceNotFound("Restaurant not found"));
    }

    @Override
    public void deleteRestaurantById(Long id) {
        restaurantRepository.findById(id)
                .ifPresentOrElse(restaurantRepository::delete, () -> {
                    throw new ResourceNotFound("Restaurant not found");
                });
    }
}
