package com.govind.foodorder.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCustomerRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;

}
