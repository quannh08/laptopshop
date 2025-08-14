package com.laptopshop.laptopshop.controller;

import com.laptopshop.laptopshop.dto.request.RoleRequest;
import com.laptopshop.laptopshop.dto.request.UserCreationRequest;
import com.laptopshop.laptopshop.repository.PermissionRepository;
import com.laptopshop.laptopshop.service.RoleService;
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
@RequestMapping("/roles")
@Tag(name = "Role Controller")
@Slf4j(topic = "ROLE-CONTROLLER")
@RequiredArgsConstructor
@Validated
public class RoleController {
    private final RoleService roleService;


    @Operation(summary = "Create Role", description = "Api add new role to database")
    @PostMapping("/add")
    public ResponseEntity<Object> createRole(@RequestBody RoleRequest request){
        log.info("Create Role: {}",request);

        Map<String, Object> result =new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message","Role created successfully");
        result.put("data",roleService.create(request));

        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @Operation(summary = "Get all Role", description = "")
    @PostMapping("/")
    public ResponseEntity<Object> getAllRole(){
        log.info("Get all Role");

        Map<String, Object> result =new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message","Get all role");
        result.put("data",roleService.getAll());

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Operation(summary = "Delete Role", description = "")
    @PostMapping("/del/{role}")
    public ResponseEntity<Object> createRole(@PathVariable String role){
        log.info("Delete Role: {}",role);

        roleService.delete(role);

        Map<String, Object> result =new LinkedHashMap<>();
        result.put("status", HttpStatus.RESET_CONTENT.value());
        result.put("message","Role deleted successfully");
        result.put("data","");

        return new ResponseEntity<>(result,HttpStatus.RESET_CONTENT);
    }

}
