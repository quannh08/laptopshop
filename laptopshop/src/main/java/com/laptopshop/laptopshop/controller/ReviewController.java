package com.laptopshop.laptopshop.controller;

import com.laptopshop.laptopshop.dto.request.ReviewRequest;
import com.laptopshop.laptopshop.exception.ResourceNotFoundException;
import com.laptopshop.laptopshop.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/review")
@Slf4j(topic = "REVIEW-CONTROLLER")
@Tag(name = "Review controller")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "Get review by product", description = "Api retrieve review by productId")
    @GetMapping("/{productId}")
    public ResponseEntity<Object> getReviewByProduct(@PathVariable Long productId,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size
                                                     ){
        log.info("Get review by product Id: {}",productId);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message","Get review by productId");
        result.put("data", reviewService.getByProductId(productId,page,size));

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Operation(summary = "Get average rating")
    @GetMapping("/rating/{productId}")
    public ResponseEntity<Object> getAverageRatingByProduct(@PathVariable Long productId){
        log.info("Get average rating");

        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status",HttpStatus.OK.value());
        result.put("message","Get average rating by productID: "+productId);
        result.put("data",reviewService.findAverageRatingByProductId(productId));

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Operation(summary = "Create review")
    @PostMapping("/add")
    public ResponseEntity<Object> createReview(@RequestBody ReviewRequest req){
        log.info("Add new review");

        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status",HttpStatus.CREATED.value());
        result.put("message","Review creation successfully");
        result.put("data",reviewService.save(req));

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(summary = "Update review")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateReview(@RequestBody ReviewRequest req,
                                               @PathVariable Long id){
        log.info("Update review");

        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status",HttpStatus.ACCEPTED.value());
        result.put("message","Review updated successfully");
        result.put("data",reviewService.update(req,id));

        return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Delete review")
    @DeleteMapping("/del/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable Long id){
        log.info("Delete review: {}",id);

        try{
            reviewService.delete(id);
            Map<String,Object> result = new LinkedHashMap<>();
            result.put("status", HttpStatus.OK.value());
            result.put("message","review deleted successfully ");
            result.put("data","" );

            return new ResponseEntity<>(result,HttpStatus.NO_CONTENT);

        }
        catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
