package com.laptopshop.laptopshop.controller;

import com.laptopshop.laptopshop.dto.request.UserCreationRequest;
import com.laptopshop.laptopshop.dto.request.UserUpdateRequest;
import com.laptopshop.laptopshop.service.UserService;
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
@RequestMapping("/user")
@Tag(name = "User Controller")
@Slf4j(topic = "USER-CONTROLLER")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @Operation(summary = "Create User", description = "Api add new user to database")
    @PostMapping("/add")
    public ResponseEntity<Object> createUser(@RequestBody UserCreationRequest request){
        log.info("Create User: {}",request);

        Map<String, Object> result =new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message","User created successfully");
        result.put("data",userService.save(request));

        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @Operation(summary = "Get user list", description = "Api retrieve user from database")
    @GetMapping("/list")
    public ResponseEntity<Object> getList(@RequestParam(required = false) String keyword,
                                          @RequestParam(required = false) String sort,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size){
        log.info("Get user list");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "User list");
        result.put("data",userService.findAll(keyword,sort,page,size));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/myInfo")
    public ResponseEntity<Object> getMyInfo() {
        return new ResponseEntity<>(userService.getMyInfo(), HttpStatus.OK);
    }

    @Operation(summary = "Get user detail", description = "API retieve user detail by ID from database")
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserDetail(@PathVariable Long userId) {
        log.info("Get user detail by ID: {}", userId);

        userService.findById(userId);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "User");
        result.put("data", userService.findById(userId));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Update User", description = "API update user to database")
    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody UserUpdateRequest req) {
        log.info("Update user: {}", req);

        userService.update(req);

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("status", HttpStatus.ACCEPTED.value());
        res.put("message", "User updated successfully");
        res.put("data", "");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Delete User")
    @DeleteMapping("/del/{userId}")
    public Map<String, Object> deleteUser(@PathVariable Long userId) {
        log.info("Deleting user: {}", userId);

        userService.delete(userId);

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("status", HttpStatus.RESET_CONTENT.value());
        res.put("message", "User deleted successfully");
        res.put("data", "");
        return res;
    }
}
