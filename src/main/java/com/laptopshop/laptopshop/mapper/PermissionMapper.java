package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.request.PermissionRequest;
import com.laptopshop.laptopshop.dto.response.PermissionResponse;
import com.laptopshop.laptopshop.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}