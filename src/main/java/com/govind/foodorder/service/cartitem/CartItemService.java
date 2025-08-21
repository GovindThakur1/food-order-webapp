package com.govind.foodorder.service.cartitem;

import com.govind.foodorder.dto.CartItemDto;
import com.govind.foodorder.exception.ResourceNotFoundException;
import com.govind.foodorder.exception.RestaurantNameNotMatchException;
import com.govind.foodorder.model.Cart;
import com.govind.foodorder.model.CartItem;
import com.govind.foodorder.model.FoodItem;
import com.govind.foodorder.repository.CartItemRepository;
import com.govind.foodorder.repository.CartRepository;
import com.govind.foodorder.service.cart.CartService;
import com.govind.foodorder.service.food.FoodItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final FoodItemService foodItemService;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    @Override
    public void addFoodItemToCart(Long cartId, Long foodId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        FoodItem foodItem = foodItemService.getFoodItemById(foodId);

        if (cart.getRestaurantName() != null && (!cart.getRestaurantName().equals(foodItem.getRestaurant().getName()))) {
            throw new RestaurantNameNotMatchException("You already have food from another restaurant in the cart. " +
                    "The food should belong to the same restaurant.");
        }

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
        cart.setRestaurantName(foodItem.getRestaurant().getName());
        cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void removeFoodItemFromCart(Long cartId, Long foodId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, foodId);
        cart.removeCartItem(itemToRemove);
//        cartRepository.save(cart); // while using transactional, no need to call save because jpa returns the managed entity
    }

    @Transactional
    @Override
    public void updateCartItemQuantity(Long cartId, Long foodId, int quantity) {
        cartItemRepository.findByCartId(cartId)
                .stream()
                .filter(cartItem -> cartItem.getFoodItem().getId().equals(foodId))
                .findFirst()
                .map(cartItem -> {
                    cartItem.setQuantity(quantity);
                    cartItem.setTotalPrice();
                    return cartItem;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Thh food item not found in the cart"));
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        return cartItemRepository.findByCartId(cartId)
                .stream()
                .filter(cartItem -> cartItem.getFoodItem().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
    }

    @Override
    public CartItemDto convertToCartItemDto(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemDto.class);
    }
}
