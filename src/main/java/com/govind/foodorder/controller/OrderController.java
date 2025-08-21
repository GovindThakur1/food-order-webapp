package com.govind.foodorder.controller;

import com.govind.foodorder.dto.OrderDto;
import com.govind.foodorder.model.Order;
import com.govind.foodorder.response.ApiResponse;
import com.govind.foodorder.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order/customer/{customerId}")
    public ResponseEntity<ApiResponse> placeOrder(@PathVariable Long customerId) {
        Order order = orderService.placeOrder(customerId);
        OrderDto orderDto = orderService.convertToOrderDto(order);
        return ResponseEntity.status(CREATED).body(new ApiResponse("Order placed", orderDto));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        OrderDto orderDto = orderService.convertToOrderDto(order);
        return ResponseEntity.status(FOUND).body(new ApiResponse("Order found", orderDto));
    }

    @GetMapping("/order/customer/{customerId}")
    public ResponseEntity<ApiResponse> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<Order> ordersByCustomer = orderService.getOrdersByCustomer(customerId);
        if (ordersByCustomer.isEmpty())
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Orders for this customer is not available", null));

        List<OrderDto> orderDtos = ordersByCustomer.stream()
                .map(orderService::convertToOrderDto)
                .toList();

        return ResponseEntity.status(FOUND).body(new ApiResponse("Orders found", orderDtos));
    }

    @PutMapping("/order/{orderId}/preparing")
    public ResponseEntity<ApiResponse> updateOrderStatusToPreparing(@PathVariable Long orderId) {
        Order order = orderService.updateOrderStatusToPreparing(orderId);
        OrderDto orderDto = orderService.convertToOrderDto(order);
        return ResponseEntity.ok(new ApiResponse("Order status updated", orderDto));
    }
}















