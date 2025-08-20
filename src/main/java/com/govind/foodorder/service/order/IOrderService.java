package com.govind.foodorder.service.order;

import com.govind.foodorder.dto.OrderDto;
import com.govind.foodorder.model.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Long customerId);

    Order getOrderById(Long orderId);

    List<Order> getOrdersByCustomer(Long customerId);

    Order updateOrderStatusToPreparing(Long orderId);

    OrderDto convertToOrderDto(Order order);
}
