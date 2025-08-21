package com.govind.foodorder.service.review;

import com.govind.foodorder.dto.ReviewDto;
import com.govind.foodorder.exception.AlreadyExistsException;
import com.govind.foodorder.exception.ResourceNotFoundException;
import com.govind.foodorder.model.Order;
import com.govind.foodorder.model.Review;
import com.govind.foodorder.repository.ReviewRepository;
import com.govind.foodorder.request.AddReviewRequest;
import com.govind.foodorder.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {


    private final ReviewRepository reviewRepository;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Review createReview(AddReviewRequest request, Long orderId) {

        if (reviewRepository.existsByOrderId(orderId)) {
            throw new AlreadyExistsException("Review already exists for this order");
        }

        Order order = orderService.getOrderById(orderId);
        Review review = createReview(order, request.getNumberOfStars(), request.getComment());
        order.setReview(review);
        return review;
    }

    private Review createReview(Order order, int numberOfStars, String comment) {
        return Review.builder()
                .numberOfStars(numberOfStars)
                .comment(comment)
                .restaurant(order.getRestaurant())
                .customer(order.getCustomer())
                .order(order)
                .build();
    }

    @Override
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> findReviewsByCustomer(Long customerId) {
        return reviewRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Review> findReviewsByRestaurant(Long restaurantId) {
        return reviewRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<Review> findReviewsByOrder(Long orderId) {
        return reviewRepository.findByOrderId(orderId);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.findById(reviewId)
                .ifPresentOrElse(reviewRepository::delete, () -> {
                    throw new ResourceNotFoundException("Review to be deleted not found");
                });
    }

    @Override
    public ReviewDto convertToDto(Review review) {
        return modelMapper.map(review, ReviewDto.class);
    }

    @Override
    public List<ReviewDto> convertToDto(List<Review> reviews) {
        return reviews.stream()
                .map(this::convertToDto)
                .toList();
    }
}
