package com.govind.foodorder.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private FoodItem foodItem;

    public OrderItem( Order order, FoodItem foodItem, int quantity, BigDecimal price) {
        this.order = order;
        this.foodItem = foodItem;
        this.quantity = quantity;
        this.price = price;
    }
}
