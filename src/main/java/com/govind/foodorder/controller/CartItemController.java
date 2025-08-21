package com.govind.foodorder.controller;

import com.govind.foodorder.dto.CartItemDto;
import com.govind.foodorder.exception.ResourceNotFoundException;
import com.govind.foodorder.exception.RestaurantNameNotMatchException;
import com.govind.foodorder.model.Cart;
import com.govind.foodorder.model.CartItem;
import com.govind.foodorder.model.Customer;
import com.govind.foodorder.response.ApiResponse;
import com.govind.foodorder.service.cart.CartService;
import com.govind.foodorder.service.cartitem.ICartItemService;
import com.govind.foodorder.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartitems")
public class CartItemController {

    private final ICartItemService cartItemService;
    private final CustomerService customerService;
    private final CartService cartService;

    @PostMapping("/item/{foodId}/add")
    public ResponseEntity<ApiResponse> addItemToCart(@PathVariable Long foodId,
                                                     @RequestParam int quantity) {
        try {
            Customer customer = customerService.getCustomerById(5L);
            Cart cart = cartService.getCartForCustomerOrInitialize(customer);

            cartItemService.addFoodItemToCart(cart.getId(), foodId, quantity);
            return ResponseEntity.status(CREATED).body(new ApiResponse("Food item added to cart successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (RestaurantNameNotMatchException e) {
            return ResponseEntity.status(BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/item/cart/{cartId}")
    public ResponseEntity<ApiResponse> removeFoodItemFromCart(@PathVariable Long cartId, @RequestParam Long foodId) {
        try {
            cartItemService.removeFoodItemFromCart(cartId, foodId);
            return ResponseEntity.ok(new ApiResponse("Removed cart item", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/item/cart/{cartId}/food/{foodId}/update")
    public ResponseEntity<ApiResponse> updateFoodItemQuantityInCart(@PathVariable Long cartId,
                                                                    @PathVariable Long foodId,
                                                                    @RequestParam int quantity) {
        try {
            cartItemService.updateCartItemQuantity(cartId, foodId, quantity);
            return ResponseEntity.ok(new ApiResponse("Updated cart item quantity", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/item/cart/{cartId}")
    public ResponseEntity<ApiResponse> getCartItem(@PathVariable Long cartId, @RequestParam Long foodId) {
        try {
            CartItem cartItem = cartItemService.getCartItem(cartId, foodId);
            CartItemDto cartItemDto = cartItemService.convertToCartItemDto(cartItem);
            return ResponseEntity.status(FOUND).body(new ApiResponse("Found", cartItemDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


}
