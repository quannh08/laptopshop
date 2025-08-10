package com.laptopshop.laptopshop.service;

import com.laptopshop.laptopshop.dto.request.CategoryRequest;
import com.laptopshop.laptopshop.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse createCategory(CategoryRequest categoryDto);

    CategoryResponse updateCategory(Long id, CategoryRequest categoryDto);

    void deleteCategory(Long id);
}
