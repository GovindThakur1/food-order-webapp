package com.govind.foodorder.exception;

public class RestaurantNameNotMatchException extends RuntimeException {
    public RestaurantNameNotMatchException(String message) {
        super(message);
    }
}
