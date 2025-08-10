package com.laptopshop.laptopshop.service;

import com.laptopshop.laptopshop.dto.request.BrandCreationRequest;
import com.laptopshop.laptopshop.dto.response.BrandResponse;

import java.util.List;

public interface BrandService {
    List<BrandResponse> getAllBrands();

    BrandResponse getBrandById(Long id);

    BrandResponse createBrand(BrandCreationRequest brand);

    BrandResponse updateBrand(Long id,BrandResponse brand);

    void deleteBrand(Long id);
}
