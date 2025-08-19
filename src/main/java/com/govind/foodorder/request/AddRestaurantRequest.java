package com.govind.foodorder.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRestaurantRequest {
    private String name;
    private String address;
    private String tagline;
}
