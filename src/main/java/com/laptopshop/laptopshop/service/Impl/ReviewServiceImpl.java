package com.laptopshop.laptopshop.service.Impl;

import com.laptopshop.laptopshop.dto.request.ReviewRequest;
import com.laptopshop.laptopshop.dto.response.ReviewPageResponse;
import com.laptopshop.laptopshop.dto.response.ReviewResponse;
import com.laptopshop.laptopshop.entity.ProductEntity;
import com.laptopshop.laptopshop.entity.ReviewEntity;
import com.laptopshop.laptopshop.entity.UserEntity;
import com.laptopshop.laptopshop.exception.ResourceNotFoundException;
import com.laptopshop.laptopshop.repository.ProductRepository;
import com.laptopshop.laptopshop.repository.ReviewRepository;
import com.laptopshop.laptopshop.repository.UserRepository;
import com.laptopshop.laptopshop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;

    private UserRepository userRepository;

    private ProductRepository productRepository;

    @Override
    public ReviewPageResponse getByProductId(Long productId,int page, int size) {
        int pageNo=0;
        if(page>0) pageNo = page-1;

        Pageable pageable = PageRequest.of(pageNo,size);

        log.info("Get review by productId");
        Page<ReviewEntity> reviewEntities = reviewRepository.getByProductId(productId,pageable);
        return getReviewPageResponse(page,size,reviewEntities);
    }

    @Override
    public double findAverageRatingByProductId(Long productId) {

        return reviewRepository.findAverageRatingByProductId(productId);
    }

    @Override
    public long save(ReviewRequest request) {
        log.info("new Review");
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));

        ReviewEntity reviewEntity = ReviewEntity.builder()
                .rating(request.getRating())
                .comment(request.getComment())
                .user(user)
                .product(product)
                .build();

        reviewRepository.save(reviewEntity);
        return reviewEntity.getId();
    }

    @Override
    public ReviewResponse update(ReviewRequest request, Long id) {
        log.info("update review");
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Review not existed"));

        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));

        if (!review.getUser().getId().equals(request.getUserId())) {
            throw new RuntimeException("User can only update their own reviews");
        }

        //update
        if(request.getRating() != null){
            review.setRating(request.getRating());
        }
        if(request.getComment()!=null){
            review.setComment(request.getComment());
        }

        ReviewEntity updateReview = reviewRepository.save(review);


        return ReviewResponse.builder().id(updateReview.getId())
                .rating(updateReview.getRating())
                .comment(updateReview.getComment())
                .userId(updateReview.getUser().getId())
                .productId(updateReview.getProduct().getId())
                .build();
    }

    @Override
    public void delete(Long id) {
        log.info("delete review");
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Review not existed"));

        if (!review.getUser().getId().equals(id)) {
            throw new RuntimeException("User can only update their own reviews");
        }

        reviewRepository.delete(review);
    }

    private ReviewPageResponse getReviewPageResponse(int page, int size, Page<ReviewEntity> reviewEntities){
        log.info("convert review page");
        List<ReviewResponse> reviewResponses = reviewEntities.stream().map(reviewEntity -> ReviewResponse.builder()
                .id(reviewEntity.getId())
                .rating(reviewEntity.getRating())
                .comment(reviewEntity.getComment())
                .userId(reviewEntity.getUser().getId())
                .productId(reviewEntity.getProduct().getId())
                .build()
        ).toList();

        ReviewPageResponse response = new ReviewPageResponse();
        response.setPageNumber(page);
        response.setPageSize(size);
        response.setTotalPages(reviewEntities.getTotalPages());
        response.setTotalElements(response.getTotalElements());
        response.setReviewResponses(reviewResponses);
        return response;
    }
}
