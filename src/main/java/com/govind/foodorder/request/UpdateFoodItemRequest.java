package com.govind.foodorder.request;

import com.govind.foodorder.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class UpdateFoodItemRequest {

    private String name;
    private BigDecimal price;
    private String description;
    private Category category;

}
