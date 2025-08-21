package com.govind.foodorder.exception;

import com.govind.foodorder.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException e) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(new ApiResponse(e.getMessage(), null));
    }


    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleAlreadyExists(AlreadyExistsException e) {
        return ResponseEntity
                .status(CONFLICT)
                .body(new ApiResponse(e.getMessage(), null));
    }

    @ExceptionHandler(RestaurantNameNotMatchException.class)
    public ResponseEntity<ApiResponse> handleRestaurantNameNotMatch(RestaurantNameNotMatchException e) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ApiResponse(e.getMessage(), null));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(new ApiResponse(e.getMessage(), null));
    }

    @ExceptionHandler(Exception.class) // fallback- it will catch all the exceptions
    public ResponseEntity<ApiResponse> handleException(Exception ex) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(ex.getMessage(), null));
    }

}
