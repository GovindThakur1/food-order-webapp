package com.govind.foodorder.controller;

import com.govind.foodorder.dto.CustomerDto;
import com.govind.foodorder.exception.AlreadyExistsException;
import com.govind.foodorder.exception.ResourceNotFoundException;
import com.govind.foodorder.model.Customer;
import com.govind.foodorder.request.CreateCustomerRequest;
import com.govind.foodorder.request.UpdateCustomerRequest;
import com.govind.foodorder.response.ApiResponse;
import com.govind.foodorder.service.customer.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/customers")
public class CustomerController {

    private final ICustomerService customerService;


    @PostMapping("/customer/create")
    public ResponseEntity<ApiResponse> createCustomer(@RequestBody CreateCustomerRequest request) {
        try {
            Customer customer = customerService.createCustomer(request);
            CustomerDto customerDto = customerService.convertToCustomerDto(customer);
            return ResponseEntity.status(CREATED).body(new ApiResponse("Customer created", customerDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/customer/all")
    public ResponseEntity<ApiResponse> getAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            List<CustomerDto> customerDtos = customerService.convertToCustomerDto(customers);
            return ResponseEntity.status(FOUND).body(new ApiResponse("Found", customerDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse> getCustomerById(@PathVariable Long customerId) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            CustomerDto customerDto = customerService.convertToCustomerDto(customer);
            return ResponseEntity.status(FOUND).body(new ApiResponse("Found", customerDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/customer/{customerId}/update")
    public ResponseEntity<ApiResponse> updateCustomer(@RequestBody UpdateCustomerRequest request, @PathVariable Long customerId) {
        try {
            Customer customer = customerService.updateCustomer(request, customerId);
            CustomerDto customerDto = customerService.convertToCustomerDto(customer);
            return ResponseEntity.ok(new ApiResponse("Updated", customerDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/customer/{customerId}/delete")
    public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable Long customerId) {
        try {
            customerService.deleteCustomer(customerId);
            return ResponseEntity.ok(new ApiResponse("Deleted", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
