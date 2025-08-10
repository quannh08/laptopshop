package com.laptopshop.laptopshop.repository;

import com.laptopshop.laptopshop.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity,Long> {
    Optional<CartEntity> findByUserId(Long userId);

    List<CartEntity> findAllByUserId(Long userId);
}
