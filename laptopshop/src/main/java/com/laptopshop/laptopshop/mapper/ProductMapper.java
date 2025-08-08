package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.request.ProductCreationRequest;
import com.laptopshop.laptopshop.dto.response.ProductResponse;
import com.laptopshop.laptopshop.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {CategoryMapper.class, BrandMapper.class})
public interface ProductMapper {
    @Mapping(target = "brandResponse", source = "brand")
    @Mapping(target = "categoryResponses", source = "categories")
    ProductResponse toProductResponse(ProductEntity product);

//    ProductEntity toProduct(ProductCreationRequest prd);
}
