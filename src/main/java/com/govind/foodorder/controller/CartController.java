package com.govind.foodorder.controller;

import com.govind.foodorder.dto.CartDto;
import com.govind.foodorder.model.Cart;
import com.govind.foodorder.response.ApiResponse;
import com.govind.foodorder.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/carts")
public class CartController {

    private final ICartService cartService;

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
        Cart cart = cartService.getCart(cartId);
        CartDto cartDto = cartService.convertToCartDto(cart);
        return ResponseEntity.status(FOUND).body(new ApiResponse("Cart Found", cartDto));
    }

    @DeleteMapping("/cart/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok(new ApiResponse("Cart cleared", null));
    }

    @GetMapping("/cart/{cartId}/total-price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId) {
        BigDecimal totalAmount = cartService.getTotalPrice(cartId);
        return ResponseEntity.ok(new ApiResponse("Success!", totalAmount));
    }

    @GetMapping("/cart/customer/{customerId}")
    public ResponseEntity<ApiResponse> getCartByCustomerId(@PathVariable Long customerId) {
        Cart cart = cartService.getCartByCustomerId(customerId);
        CartDto cartDto = cartService.convertToCartDto(cart);
        return ResponseEntity.status(FOUND).body(new ApiResponse("Found", cartDto));
    }
}
