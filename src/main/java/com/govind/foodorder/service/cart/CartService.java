package com.govind.foodorder.service.cart;

import com.govind.foodorder.exception.ResourceNotFoundException;
import com.govind.foodorder.model.Cart;
import com.govind.foodorder.model.Customer;
import com.govind.foodorder.repository.CartRepository;
import com.govind.foodorder.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Cart getCart(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        cart.getCartItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        return getCart(id).getTotalAmount();
    }

    @Override
    public Cart getCartByCustomerId(Long customerId) {
        return cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    @Override
    public Cart getCartForCustomerOrInitialize(Customer customer) {
        return cartRepository.findByCustomerId(customer.getId())
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setCustomer(customer);
                    cartRepository.save(cart);
                    customer.setCart(cart);
                    customerRepository.save(customer);
                    return cart;
                });
    }
}
