package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.request.BrandCreationRequest;
import com.laptopshop.laptopshop.dto.response.BrandResponse;
import com.laptopshop.laptopshop.entity.BrandEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandResponse toBrandResponse(BrandEntity brand);

    BrandEntity toBrand(BrandCreationRequest request);
}
