package com.govind.foodorder.service.cartitem;

import com.govind.foodorder.dto.CartItemDto;
import com.govind.foodorder.model.CartItem;

public interface ICartItemService {

    void addFoodItemToCart(Long cartId, Long foodId, int quantity);

    void removeFoodItemFromCart(Long cartId, Long foodId);

    void updateCartItemQuantity(Long cartId, Long foodId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);

    CartItemDto convertToCartItemDto(CartItem cartItem);
}
