package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.request.CategoryRequest;
import com.laptopshop.laptopshop.dto.response.CategoryResponse;
import com.laptopshop.laptopshop.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toCategoryResponse(CategoryEntity category);

    CategoryEntity toCategory(CategoryRequest categoryRequest);
}
