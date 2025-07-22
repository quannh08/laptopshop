package com.laptopshop.laptopshop.repository;

import com.laptopshop.laptopshop.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    ProductEntity findByName(String name);

    @Query("SELECT DISTINCT p FROM ProductEntity p JOIN p.categories c WHERE c.id IN :categoryIds")
    List<ProductEntity> findByCategories_Id(Set<Long> categoryIds);

    List<ProductEntity> findByBrandId(Long brandId);

    @Query("SELECT p FROM ProductEntity p " +
            "WHERE (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:categoryId IS NULL OR EXISTS ( SELECT c FROM p.categories c WHERE c.id = :categoryId " +
            "      ))" +
            "AND (:brandId IS NULL OR p.brand.id = :brandId)")
    Page<ProductEntity> searchByKeywordAndPriceRange(
            @Param("keyword") String keyword,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("brandId") Long brandId,
            @Param("categoryId") Long categoryId,
            Pageable pageable);
}
