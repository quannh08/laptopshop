package com.laptopshop.laptopshop.repository;

import com.laptopshop.laptopshop.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {
    Page<ReviewEntity> getByProductId(Long productId, Pageable pageable);

    List<ReviewEntity> findByProductId(Long productId);

    List<ReviewEntity> findByUserId(Long userId);

    @Query("SELECT AVG(r.rating) FROM ReviewEntity r WHERE r.product.id = :productId")
    double findAverageRatingByProductId(Long productId);
}
