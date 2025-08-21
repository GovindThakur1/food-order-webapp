package com.govind.foodorder.controller;

import com.govind.foodorder.dto.ReviewDto;
import com.govind.foodorder.model.Review;
import com.govind.foodorder.request.AddReviewRequest;
import com.govind.foodorder.response.ApiResponse;
import com.govind.foodorder.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/reviews")
public class ReviewController {


    private final ReviewService reviewService;

    @PostMapping("/review/order/{orderId}")
    public ResponseEntity<ApiResponse> addReview(@RequestBody AddReviewRequest request,
                                                 @PathVariable Long orderId) {
        Review review = reviewService.createReview(request, orderId);
        ReviewDto reviewDto = reviewService.convertToDto(review);
        return ResponseEntity.status(CREATED)
                .body(new ApiResponse("Review added", reviewDto));
    }

    @GetMapping("/review/all")
    public ResponseEntity<ApiResponse> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        List<ReviewDto> reviewDtos = reviewService.convertToDto(reviews);
        return ResponseEntity.status(FOUND)
                .body(new ApiResponse("Found", reviewDtos));
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ApiResponse> getReviewById(@PathVariable Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        ReviewDto reviewDto = reviewService.convertToDto(review);
        return ResponseEntity.status(FOUND)
                .body(new ApiResponse("Review found", reviewDto));
    }

    @GetMapping("/review/customer/{customerId}")
    public ResponseEntity<ApiResponse> getReviewsByCustomer(@PathVariable Long customerId) {
        List<Review> reviews = reviewService.findReviewsByCustomer(customerId);
        List<ReviewDto> reviewDtos = reviewService.convertToDto(reviews);
        return ResponseEntity.status(FOUND)
                .body(new ApiResponse("Reviews found", reviewDtos));
    }

    @GetMapping("/review/restaurant/{restaurantId}")
    public ResponseEntity<ApiResponse> getReviewsByRestaurant(@PathVariable Long restaurantId) {
        List<Review> reviews = reviewService.findReviewsByRestaurant(restaurantId);
        List<ReviewDto> reviewDtos = reviewService.convertToDto(reviews);
        return ResponseEntity.status(FOUND)
                .body(new ApiResponse("Reviews found", reviewDtos));
    }

    @GetMapping("/review/order/{orderId}")
    public ResponseEntity<ApiResponse> getReviewsByOrder(@PathVariable Long orderId) {
        List<Review> reviews = reviewService.findReviewsByOrder(orderId);
        List<ReviewDto> reviewDtos = reviewService.convertToDto(reviews);
        return ResponseEntity.status(FOUND)
                .body(new ApiResponse("Reviews found", reviewDtos));
    }

    @DeleteMapping("/review/{reviewId}/delete")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(new ApiResponse("Review deleted", null));
    }


}
