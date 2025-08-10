package com.laptopshop.laptopshop.controller;

import com.laptopshop.laptopshop.dto.request.BrandCreationRequest;
import com.laptopshop.laptopshop.dto.response.BrandResponse;
import com.laptopshop.laptopshop.exception.ResourceNotFoundException;
import com.laptopshop.laptopshop.service.BrandService;
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
@RequestMapping("/brand")
@Tag(name = "Brand-Controller")
@Slf4j(topic = "BRAND CONTROLLER")
@RequiredArgsConstructor
@Validated
public class BrandController {
    private final BrandService brandService;

    @Operation(summary = "Get all brand", description = "Api retrieve brand from database")
    @GetMapping("/list")
    public ResponseEntity<Object> getAllBrands(){
        List<BrandResponse> brandResponses=brandService.getAllBrands();

        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message","Get all brand");
        result.put("data", brandResponses);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Operation(summary = "Get brand by id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getBrandById(@PathVariable Long id){
        BrandResponse brand = brandService.getBrandById(id);

        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message","Get brand by id: "+ id);
        result.put("data", brand);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Create brand")
    @PostMapping("/add")
    public ResponseEntity<Object> createBrand(@RequestBody BrandCreationRequest req){

        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message","Brand created successfully ");
        result.put("data", brandService.createBrand(req));

        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @Operation(summary = "Update brand")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateBrand(@PathVariable Long id ,@RequestBody BrandResponse brand){
        try{
            Map<String,Object> result = new LinkedHashMap<>();
            result.put("status", HttpStatus.OK.value());
            result.put("message","Brand updated successfully ");
            result.put("data", brandService.updateBrand(id,brand));

            return new ResponseEntity<>(result,HttpStatus.OK);

        }
        catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @Operation(summary = "Delete brand")
    @DeleteMapping("/del/{id}")
    public ResponseEntity<Object> deleteBrand(@PathVariable Long id){
        try{
            brandService.deleteBrand(id);
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
