package com.laptopshop.laptopshop.controller;

import com.laptopshop.laptopshop.dto.request.ProductCreationRequest;
import com.laptopshop.laptopshop.dto.request.ProductUpdateRequest;
import com.laptopshop.laptopshop.dto.response.ProductResponse;
import com.laptopshop.laptopshop.exception.ResourceNotFoundException;
import com.laptopshop.laptopshop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/product")
@Tag(name = "Product-controller")
@Slf4j(topic = "PRODUCT CONTROLLER")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get product list", description = "Api retrieve product from database")
    @GetMapping("/list")
    public ResponseEntity<Object> getListProduct(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false)BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
            ){
        log.info("Get product List");
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "Product list");
        result.put("data", productService.findAll(keyword, sort, page, size, minPrice, maxPrice, brandId, categoryId));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get Product detail", description = "API retrieve product detail by Id from database")
    @GetMapping("/{productId}")
    public ResponseEntity<Object> getProductDetail(@PathVariable Long productId) {
        log.info("Get product detail by Id: {}", productId);

        ProductResponse productDetail = productService.findById(productId);

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("status", HttpStatus.OK.value());
        res.put("message", "Product");
        res.put("data", productDetail);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Create Product", description = "Api add new product to database")
    @PostMapping("/add")
    public ResponseEntity<Object> createProduct(
            @RequestPart("product") ProductCreationRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        log.info("Create Product: {}", request);

        Map<String, Object> result = new LinkedHashMap<>();
        try {
            result.put("status", HttpStatus.CREATED.value());
            result.put("message", "Product created successfully");
            result.put("data", productService.save(request, image));
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (IOException e) {
            result.put("status", HttpStatus.BAD_REQUEST.value());
            result.put("message", "Failed to process image file");
            result.put("data", null);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update Product", description = "API update product to database")
    @PutMapping("/update")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductUpdateRequest product,
                                                @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        log.info("Update product: {}", product);

        try{
            productService.update(product,image);

            Map<String, Object> res = new LinkedHashMap<>();
            res.put("status", HttpStatus.ACCEPTED.value());
            res.put("message", "Product updated successfully");
            res.put("data", "");

            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @Operation(summary = "Delete Product")
    @DeleteMapping("/del/{productId}")
    public  ResponseEntity<Object> deleteProduct(@PathVariable Long productId) {
        log.info("Deleting product: {}", productId);

        try{
            productService.delete(productId);
            Map<String,Object> result = new LinkedHashMap<>();
            result.put("status", HttpStatus.OK.value());
            result.put("message","Brand updated successfully ");
            result.put("data","" );

            return new ResponseEntity<>(result,HttpStatus.NO_CONTENT);

        }
        catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
