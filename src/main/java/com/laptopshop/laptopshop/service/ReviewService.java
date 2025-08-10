package com.laptopshop.laptopshop.service;

import com.laptopshop.laptopshop.dto.request.ReviewRequest;
import com.laptopshop.laptopshop.dto.response.ReviewPageResponse;
import com.laptopshop.laptopshop.dto.response.ReviewResponse;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    ReviewPageResponse getByProductId(Long productId,int page,int size);

    double findAverageRatingByProductId(Long productId);

    long save (ReviewRequest request);

    ReviewResponse update(ReviewRequest request, Long id);

    void delete (Long id);
}
