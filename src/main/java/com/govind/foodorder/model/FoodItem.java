package com.govind.foodorder.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private int inventory;
    private String description;


    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "foodItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    public FoodItem(String name, BigDecimal price, int inventory, String description, Restaurant restaurant, Category category) {
        this.name = name;
        this.price = price;
        this.inventory = inventory;
        this.description = description;
        this.restaurant = restaurant;
        this.category = category;
    }
}
