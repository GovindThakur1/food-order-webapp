package com.govind.foodorder.controller;

import com.govind.foodorder.exception.ResourceNotFoundException;
import com.govind.foodorder.model.Cart;
import com.govind.foodorder.model.Customer;
import com.govind.foodorder.response.ApiResponse;
import com.govind.foodorder.service.cart.CartService;
import com.govind.foodorder.service.cartitem.ICartItemService;
import com.govind.foodorder.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
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
            Customer customer = customerService.getCustomerById(8L);
            Cart cart = cartService.getCartForCustomerOrInitialize(customer);

            cartItemService.addFoodItemToCart(cart.getId(), foodId, quantity);
            return ResponseEntity.status(CREATED).body(new ApiResponse("Food item added to cart successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }


}
