package com.laptopshop.laptopshop.repository;

import com.laptopshop.laptopshop.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail,Long> {
    List<CartDetail> findByCartId(Long cartId);
    Optional<CartDetail> findByCartIdAndProductId(Long cartId, Long productId);

    @Query("SELECT SUM(cd.quantity) FROM CartDetail cd WHERE cd.cart.id = :cartId")
    Integer getTotalQuantityByCartId(@Param("cartId") Long cartId);
}
