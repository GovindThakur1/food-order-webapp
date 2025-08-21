package com.govind.foodorder.service.review;

import com.govind.foodorder.dto.ReviewDto;
import com.govind.foodorder.model.Review;
import com.govind.foodorder.request.AddReviewRequest;

import java.util.List;

public interface IReviewService {

    Review createReview(AddReviewRequest request, Long orderId);

    Review getReviewById(Long reviewId);

    List<Review> getAllReviews();

    List<Review> findReviewsByCustomer(Long customerId);

    List<Review> findReviewsByRestaurant(Long restaurantId);

    List<Review> findReviewsByOrder(Long orderId);

    void deleteReview(Long reviewId);

    ReviewDto convertToDto(Review review);

    List<ReviewDto> convertToDto(List<Review> reviews);
}
