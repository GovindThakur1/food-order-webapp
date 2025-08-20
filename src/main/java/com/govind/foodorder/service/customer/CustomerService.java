package com.govind.foodorder.service.customer;

import com.govind.foodorder.dto.CustomerDto;
import com.govind.foodorder.exception.AlreadyExistsException;
import com.govind.foodorder.exception.ResourceNotFoundException;
import com.govind.foodorder.model.Customer;
import com.govind.foodorder.model.Role;
import com.govind.foodorder.repository.CustomerRepository;
import com.govind.foodorder.repository.RoleRepository;
import com.govind.foodorder.request.CreateCustomerRequest;
import com.govind.foodorder.request.UpdateCustomerRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public Customer createCustomer(CreateCustomerRequest request) {
        return Optional.ofNullable(request)
                .map(CreateCustomerRequest::getEmail)
                .filter(email -> !customerRepository.existsByEmail(email))
                .map(email -> {
                    Role role = roleRepository.findByName("ROLE_CUSTOMER")
                            .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

                    Customer customer = Customer.builder()
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .address(request.getAddress())
                            .phone(request.getPhone())
                            .email(email)
                            .password(request.getPassword())
                            .role(role)
                            .build();

                    return customerRepository.save(customer);
                })
                .orElseThrow(() -> new AlreadyExistsException("User with this email already exists"));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }

    @Override
    public Customer updateCustomer(UpdateCustomerRequest request, Long id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    if (request.getFirstName() != null)
                        customer.setFirstName(request.getFirstName());
                    if (request.getLastName() != null)
                        customer.setLastName(request.getLastName());
                    if (request.getAddress() != null)
                        customer.setLastName(request.getLastName());
                    if (request.getPhone() != null)
                        customer.setPhone(request.getPhone());
                    return customerRepository.save(customer);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.findById(id)
                .ifPresentOrElse(customerRepository::delete, () -> {
                    throw new ResourceNotFoundException("Customer not found");
                });
    }

    @Override
    public CustomerDto convertToCustomerDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    @Override
    public List<CustomerDto> convertToCustomerDto(List<Customer> customers) {
        return customers.stream()
                .map(this::convertToCustomerDto)
                .toList();
    }
}
