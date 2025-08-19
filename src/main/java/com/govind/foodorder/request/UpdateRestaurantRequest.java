package com.govind.foodorder.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class UpdateRestaurantRequest {
    private String name;
    private String address;
    private String tagline;
}
