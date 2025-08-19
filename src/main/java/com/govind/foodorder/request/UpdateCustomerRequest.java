package com.govind.foodorder.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCustomerRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
}
