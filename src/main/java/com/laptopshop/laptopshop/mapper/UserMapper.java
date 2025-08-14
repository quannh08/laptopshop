package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.request.UserCreationRequest;
import com.laptopshop.laptopshop.dto.request.UserUpdateRequest;
import com.laptopshop.laptopshop.dto.response.RoleResponse;
import com.laptopshop.laptopshop.dto.response.UserResponse;
import com.laptopshop.laptopshop.entity.Role;
import com.laptopshop.laptopshop.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",uses = {RoleMapper.class})
public  interface UserMapper {
    @Mapping(target = "status", expression = "java(user.getStatus().name())")
    UserResponse toUserResponse(UserEntity user);

    // DTO -> Entity
    @Mapping(target = "roles", expression = "java(mapStringsToRoles(user.getRoles()))")
    UserEntity toUser(UserCreationRequest user);


    // Helper: List<String> -> Set<Role>
    default Set<Role> mapStringsToRoles(Set<String> roleNames) {
        if (roleNames == null) return null;
        return roleNames.stream()
                .map(name -> {
                    Role role = new Role();
                    role.setName(name); // set tên role
                    return role;
                })
                .collect(Collectors.toSet());
    }
}
