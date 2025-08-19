package com.govind.foodorder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String restaurantName;
    private CategoryDto category;
    private List<ImageDto> images;
}
