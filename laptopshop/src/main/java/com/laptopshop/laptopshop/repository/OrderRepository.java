package com.laptopshop.laptopshop.repository;

import com.laptopshop.laptopshop.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    Page<OrderEntity> findByUserId(Long userId, Pageable pageable);

    List<OrderEntity> findByStatus(String status);
}
