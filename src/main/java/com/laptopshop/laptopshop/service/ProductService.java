package com.laptopshop.laptopshop.service;

import com.laptopshop.laptopshop.dto.request.ProductCreationRequest;
import com.laptopshop.laptopshop.dto.request.ProductUpdateRequest;
import com.laptopshop.laptopshop.dto.response.ProductPageResponse;
import com.laptopshop.laptopshop.dto.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ProductService {
    ProductPageResponse findAll(String keyword, String sort, int page, int size, BigDecimal minPrice, BigDecimal maxPrice,
                                Long brandId, Long categoryId);

    ProductResponse findById(Long id);

    ProductResponse findByName(String name);

    ProductPageResponse findByPriceBetween(Double minPrice, Double maxPrice);

    List<ProductResponse> findByBrandId(Long brandId);

    List<ProductResponse> findByCategoryId(Set<Long> categoryId);

    long save(ProductCreationRequest req, MultipartFile imageFile) throws IOException;

    void update(ProductUpdateRequest req, MultipartFile imageFile) throws IOException;

    void delete(Long id);

    public boolean updateStock(Long productId, Integer quantity);
}
