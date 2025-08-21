package com.govind.foodorder.controller;

import com.govind.foodorder.dto.CartItemDto;
import com.govind.foodorder.model.Cart;
import com.govind.foodorder.model.CartItem;
import com.govind.foodorder.model.Customer;
import com.govind.foodorder.response.ApiResponse;
import com.govind.foodorder.service.cart.CartService;
import com.govind.foodorder.service.cartitem.ICartItemService;
import com.govind.foodorder.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;

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
        Customer customer = customerService.getCustomerById(5L);
        Cart cart = cartService.getCartForCustomerOrInitialize(customer);

        cartItemService.addFoodItemToCart(cart.getId(), foodId, quantity);
        return ResponseEntity.status(CREATED).body(new ApiResponse("Food item added to cart successfully", null));
    }

    @DeleteMapping("/item/cart/{cartId}")
    public ResponseEntity<ApiResponse> removeFoodItemFromCart(@PathVariable Long cartId, @RequestParam Long foodId) {
        cartItemService.removeFoodItemFromCart(cartId, foodId);
        return ResponseEntity.ok(new ApiResponse("Removed cart item", null));
    }

    @PutMapping("/item/cart/{cartId}/food/{foodId}/update")
    public ResponseEntity<ApiResponse> updateFoodItemQuantityInCart(@PathVariable Long cartId,
                                                                    @PathVariable Long foodId,
                                                                    @RequestParam int quantity) {
        cartItemService.updateCartItemQuantity(cartId, foodId, quantity);
        return ResponseEntity.ok(new ApiResponse("Updated cart item quantity", null));
    }


    @GetMapping("/item/cart/{cartId}")
    public ResponseEntity<ApiResponse> getCartItem(@PathVariable Long cartId, @RequestParam Long foodId) {
        CartItem cartItem = cartItemService.getCartItem(cartId, foodId);
        CartItemDto cartItemDto = cartItemService.convertToCartItemDto(cartItem);
        return ResponseEntity.status(FOUND).body(new ApiResponse("Found", cartItemDto));
    }


}
