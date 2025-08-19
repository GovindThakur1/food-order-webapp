package com.govind.foodorder.service.cartitem;

import com.govind.foodorder.model.Cart;
import com.govind.foodorder.model.CartItem;
import com.govind.foodorder.model.FoodItem;
import com.govind.foodorder.repository.CartItemRepository;
import com.govind.foodorder.repository.CartRepository;
import com.govind.foodorder.service.cart.CartService;
import com.govind.foodorder.service.food.FoodItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final FoodItemService foodItemService;
    private final CartRepository cartRepository;

    @Override
    public void addFoodItemToCart(Long cartId, Long foodId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        FoodItem foodItem = foodItemService.getFoodItemById(foodId);

        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(existingCartItem -> existingCartItem.getFoodItem().getId().equals(foodId))
                .findFirst()
                .orElse(new CartItem());

        if (cartItem.getId() == null) {
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(foodItem.getPrice());
            cartItem.setFoodItem(foodItem);
            cartItem.setCart(cart);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addCartItem(cartItem);
        // may be need cartitemresp.save(cartitem)
        cartRepository.save(cart);
    }

    @Override
    public void removeFoodItemFromCart(Long cartId, Long foodId) {

    }

    @Override
    public void updateCartItemQuantity(Long cartId, Long foodId, int quantity) {

    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        return null;
    }
}
