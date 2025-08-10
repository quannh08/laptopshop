package com.laptopshop.laptopshop.service.Impl;

import com.laptopshop.laptopshop.dto.request.BrandCreationRequest;
import com.laptopshop.laptopshop.dto.response.BrandResponse;
import com.laptopshop.laptopshop.entity.BrandEntity;
import com.laptopshop.laptopshop.exception.ResourceNotFoundException;
import com.laptopshop.laptopshop.mapper.BrandMapper;
import com.laptopshop.laptopshop.repository.BrandRepository;
import com.laptopshop.laptopshop.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    private final BrandMapper brandMapper;

    @Override
    public List<BrandResponse> getAllBrands() {
        List<BrandEntity>brands =brandRepository.findAll();

        return brands.stream()
                .map(brandMapper::toBrandResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BrandResponse getBrandById(Long id) {
        BrandEntity brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: "+id));
        return brandMapper.toBrandResponse(brand);
    }

    @Override
    public BrandResponse createBrand(BrandCreationRequest brand) {
        BrandEntity brandEntity = brandMapper.toBrand(brand);
        brandRepository.save(brandEntity);

        return brandMapper.toBrandResponse(brandEntity);
    }

    @Override
    public BrandResponse updateBrand(Long id, BrandResponse brand) {
        BrandEntity brandEntity = brandRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Brand not found with id: "+ id));
        brandEntity.setName(brand.getName());
        BrandEntity updatedBrand =brandRepository.save(brandEntity);
        return brandMapper.toBrandResponse(updatedBrand);
    }

    @Override
    public void deleteBrand(Long id) {
        BrandEntity brand = brandRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Brand not found with id: "+id));
        brandRepository.delete(brand);
    }
}
