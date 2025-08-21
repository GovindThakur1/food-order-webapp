package com.govind.foodorder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private int numberOfStars;
    private String comment;
    private String restaurantName;
    private CustomerDto customer;
    private LocalDate orderDate;
}
