package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.request.RoleRequest;
import com.laptopshop.laptopshop.dto.response.RoleResponse;
import com.laptopshop.laptopshop.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}