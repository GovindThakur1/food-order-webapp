package com.govind.foodorder.service.cart;

import com.govind.foodorder.model.Cart;
import com.govind.foodorder.model.Customer;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Cart getCartByCustomerId(Long customerId);

    Cart getCartForCustomerOrInitialize(Customer customer);

}
