package com.govind.foodorder.repository;

import com.govind.foodorder.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    boolean existsByName(String name);

    Restaurant findByAddress(String address);
}
