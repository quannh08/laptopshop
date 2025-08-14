package com.laptopshop.laptopshop.controller;

import com.laptopshop.laptopshop.dto.request.PermissionRequest;
import com.laptopshop.laptopshop.dto.request.UserCreationRequest;
import com.laptopshop.laptopshop.service.PermissionService;
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
@RequestMapping("/permissions")
@Tag(name = "Permission Controller")
@Slf4j(topic = "PERMISSION-CONTROLLER")
@RequiredArgsConstructor
@Validated
public class PermissionController {
    private final PermissionService permissionService;

    @Operation(summary = "Create Permission", description = "Api add new permission to database")
    @PostMapping("/add")
    public ResponseEntity<Object> createPermission(@RequestBody PermissionRequest request){
        log.info("Create Permission: {}",request);

        Map<String, Object> result =new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message","Permission created successfully");
        result.put("data",permissionService.create(request));

        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @Operation(summary = "Get all Permission", description = "")
    @PostMapping("/")
    public ResponseEntity<Object> getAllPermission(){
        log.info("Get all permission");

        Map<String, Object> result =new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message","Get all permission");
        result.put("data",permissionService.getAll());

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Operation(summary = "Delete Permission", description = "")
    @PostMapping("/del/{permission}")
    public ResponseEntity<Object> deletePermission(@PathVariable String permission){
        log.info("Delete Permission: {}",permission);

        permissionService.delete(permission);

        Map<String, Object> result =new LinkedHashMap<>();
        result.put("status", HttpStatus.RESET_CONTENT.value());
        result.put("message","Permission deleted successfully");
        result.put("data","");

        return new ResponseEntity<>(result,HttpStatus.RESET_CONTENT);
    }
}
