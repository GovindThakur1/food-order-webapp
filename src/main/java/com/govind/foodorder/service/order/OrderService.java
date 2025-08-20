package com.govind.foodorder.service.order;

import com.govind.foodorder.enums.OrderStatus;
import com.govind.foodorder.exception.ResourceNotFoundException;
import com.govind.foodorder.model.*;
import com.govind.foodorder.repository.OrderRepository;
import com.govind.foodorder.service.cart.CartService;
import com.govind.foodorder.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CustomerService customerService;

    @Transactional
    @Override
    public Order placeOrder(Long customerId) {
        Cart cart = cartService.getCartByCustomerId(customerId);
        Customer customer = customerService.getCustomerById(customerId);

        Order order = createOrder(cart, customer);
        List<OrderItem> orderItems = createOrderItems(order, cart);

        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(calculateTotalAmount(orderItems));

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return savedOrder;
    }

    private Order createOrder(Cart cart, Customer customer) {
        Restaurant restaurant = cart.getCartItems().stream()
                .map(cartItem -> cartItem.getFoodItem().getRestaurant())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
        return new Order(LocalDate.now(), OrderStatus.PENDING, customer, restaurant);
    }


    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getCartItems()
                .stream()
                .map(cartItem -> {
                    return new OrderItem(
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice(),
                            cartItem.getTotalPrice(),
                            order,
                            cartItem.getFoodItem()
                    );
                })
                .toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> orderItem
                        .getUnitPrice()
                        .multiply(new BigDecimal(orderItem.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<Order> getOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Transactional
    @Override
    public Order updateOrderStatusToPreparing(Long orderId) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.setOrderStatus(OrderStatus.PREPARING);
                    return order;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }
}
