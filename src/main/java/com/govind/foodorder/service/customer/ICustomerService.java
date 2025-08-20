package com.govind.foodorder.service.customer;

import com.govind.foodorder.dto.CustomerDto;
import com.govind.foodorder.model.Customer;
import com.govind.foodorder.request.CreateCustomerRequest;
import com.govind.foodorder.request.UpdateCustomerRequest;

import java.util.List;

public interface ICustomerService {

    Customer createCustomer(CreateCustomerRequest request);

    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

    Customer updateCustomer(UpdateCustomerRequest request, Long id);

    void deleteCustomer(Long id);

    CustomerDto convertToCustomerDto(Customer customer);

    List<CustomerDto> convertToCustomerDto(List<Customer> customers);
}

