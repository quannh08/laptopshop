package com.laptopshop.laptopshop.controller;

import com.laptopshop.laptopshop.dto.request.OrderRequest;
import com.laptopshop.laptopshop.exception.ResourceNotFoundException;
import com.laptopshop.laptopshop.service.OrderService;
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
@Slf4j(topic = "ORDER-CONTROLLER")
@RequestMapping("/order")
@Tag(name = "Order controller")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Get all orders")
    @GetMapping("/list")
    public ResponseEntity<Object> getAllOrders(@RequestParam int page,
                                               @RequestParam int size){
        log.info("Get all order");

        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message","All order");
        result.put("data",orderService.getAllOrder(page,size));

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Operation(summary = "Get list order by user")
    @GetMapping("/list/{userId}")
    public ResponseEntity<Object> getOrderByUser(@PathVariable Long userId,
                                                 @RequestParam int page,
                                                 @RequestParam int size){
        log.info("Get order by userID: {}",userId);

        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message","All order");
        result.put("data",orderService.findOrderByUserId(userId,page,size));

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Operation(summary = "Get order by id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable Long id){
        log.info("Get order by id: {}",id);

        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message","Order with id: "+id);
        result.put("data",orderService.findById(id));

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Operation(summary = "Create order")
    @PostMapping("/add")
    public ResponseEntity<Object> createOrder(@RequestBody OrderRequest orderRequest){
        log.info("Add new order");

        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message","Order created  successfully ");
        result.put("data",orderService.save(orderRequest));

        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @Operation(summary = "Update order")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable Long id,@RequestBody OrderRequest orderRequest){
        log.info("Update order");
        try{
            Map<String,Object> result = new LinkedHashMap<>();
            result.put("status", HttpStatus.ACCEPTED.value());
            result.put("message","Order updated successfully ");
            result.put("data",orderService.update(id, orderRequest));

            return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
        }catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @Operation(summary = "Delete order")
    @DeleteMapping("/del/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable Long id){

        log.info("Delete order: {}",id);

        try{
            orderService.delete(id);
            Map<String,Object> result = new LinkedHashMap<>();
            result.put("status", HttpStatus.OK.value());
            result.put("message","Order deleted successfully ");
            result.put("data","" );

            return new ResponseEntity<>(result,HttpStatus.NO_CONTENT);

        }
        catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
