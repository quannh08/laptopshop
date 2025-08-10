package com.laptopshop.laptopshop.controller;

import com.laptopshop.laptopshop.dto.request.CartCreationRequest;
import com.laptopshop.laptopshop.dto.request.CartDetailCreationRequest;
import com.laptopshop.laptopshop.exception.ResourceNotFoundException;
import com.laptopshop.laptopshop.service.CartService;
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
@RequiredArgsConstructor
@Validated
@RequestMapping("/cart")
@Tag(name = "Cart controller")
@Slf4j(topic = "CART-CONTROLLER")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "Get cart by id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCartById(@PathVariable Long id){
        log.info("get cart by id: {}",id);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message","Get cart by id: "+id);
        result.put("data",cartService.getCartById(id));

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Operation(summary = "Get cart by user id")
    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getCartByUserId(@PathVariable Long id){
        log.info("get cart by userId: {}",id);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message","Get cart by UserId: "+id);
        result.put("data",cartService.getCartByUserId(id));

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Operation(summary = "Create cart")
    @PostMapping("/add")
    public ResponseEntity<Object> createCart(@RequestBody CartCreationRequest cartCreationRequest){
        log.info("Add new cart");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message","Cart create successfully");
        result.put("data",cartService.createCart(cartCreationRequest));

        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @Operation(summary = "Add product to cart")
    @PostMapping("/add-to-cart/{cartId}")
    public ResponseEntity<Object> addProductToCart(@PathVariable Long cartId
            ,@RequestBody CartDetailCreationRequest cartItem){

        log.info("Add new product to cart");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message","Add prooduct to cart successfully");
        result.put("data",cartService.addProductToCart(cartId,cartItem));

        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @Operation(summary = "Delete cartItem to cart")
    @DeleteMapping("/del/{cartItemId}")
    public ResponseEntity<Object> deleteCartItem(@PathVariable Long cartItemId){

        log.info("Delete cart item");

        try{
            cartService.deleteCartItem(cartItemId);
            Map<String,Object> result = new LinkedHashMap<>();
            result.put("status", HttpStatus.OK.value());
            result.put("message","cart item deleted successfully ");
            result.put("data","" );

            return new ResponseEntity<>(result,HttpStatus.NO_CONTENT);

        }
        catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete cartItem to cart")
    @DeleteMapping("/del/{userId}/{productId}")
    public ResponseEntity<Object> deleteCartItemByProductId(@PathVariable Long userId, @PathVariable Long ProductId){

        log.info("Delete cart item");

        try{
            cartService.deleteByUserIdAndProductId(userId, ProductId);
            Map<String,Object> result = new LinkedHashMap<>();
            result.put("status", HttpStatus.OK.value());
            result.put("message","cart item deleted successfully ");
            result.put("data","" );

            return new ResponseEntity<>(result,HttpStatus.NO_CONTENT);

        }
        catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
