package com.govind.foodorder.controller;

import com.govind.foodorder.exception.ResourceNotFoundException;
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
        try {
            Order order = orderService.placeOrder(customerId);
            return ResponseEntity.status(CREATED).body(new ApiResponse("Order placed", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try {
            Order order = orderService.getOrderById(orderId);
            return ResponseEntity.status(FOUND).body(new ApiResponse("Order found", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/order/customer/{customerId}")
    public ResponseEntity<ApiResponse> getOrdersByCustomerId(@PathVariable Long customerId) {
        try {
            List<Order> ordersByCustomer = orderService.getOrdersByCustomer(customerId);
            if (ordersByCustomer.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Orders for this customer is not available", null));
            return ResponseEntity.status(FOUND).body(new ApiResponse("Orders found", ordersByCustomer));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/order/{orderId}/preparing")
    public ResponseEntity<ApiResponse> updateOrderStatusToPreparing(@PathVariable Long orderId) {
        try {
            Order order = orderService.updateOrderStatusToPreparing(orderId);
            return ResponseEntity.ok(new ApiResponse("Order status updated", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}















