package com.laptopshop.laptopshop.controller;


import com.laptopshop.laptopshop.dto.request.CategoryRequest;
import com.laptopshop.laptopshop.dto.response.CategoryResponse;
import com.laptopshop.laptopshop.exception.ResourceNotFoundException;
import com.laptopshop.laptopshop.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/category")
@Tag(name = "Category-controller")
@Slf4j(topic = "CATEGORY CONTROLLER")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Get all categories", description = "API retrieve category from database")
    @GetMapping("/list")
    public ResponseEntity<Object> getAllcategories(){
        List<CategoryResponse> categoryResponses = categoryService.getAllCategories();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "Get all categories");
        result.put("data",categoryResponses);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get category by id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable Long id){
        try{
            CategoryResponse categoryResponse = categoryService.getCategoryById(id);

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("status", HttpStatus.OK.value());
            result.put("message", "Get category by id: "+id);
            result.put("data",categoryResponse);

            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch(ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "create category")
    @PostMapping("/add")
    public ResponseEntity<Object> createCategory(@RequestBody CategoryRequest req){

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message", "Get category by id: ");
        result.put("data",categoryService.createCategory(req));

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(summary = "Update category")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest req){
        try {
            Map<String,Object> result = new LinkedHashMap<>();
            result.put("status", HttpStatus.OK.value());
            result.put("message","Brand updated successfully ");
            result.put("data", categoryService.updateCategory(id,req));

            return new ResponseEntity<>(result,HttpStatus.OK);
        } catch(ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete category")
    @DeleteMapping("/del/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id){
        try{
            categoryService.deleteCategory(id);
            Map<String,Object> result = new LinkedHashMap<>();
            result.put("status", HttpStatus.OK.value());
            result.put("message","Brand updated successfully ");
            result.put("data", "");

            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
