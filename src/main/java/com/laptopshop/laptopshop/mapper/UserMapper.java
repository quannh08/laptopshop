package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.request.UserCreationRequest;
import com.laptopshop.laptopshop.dto.request.UserUpdateRequest;
import com.laptopshop.laptopshop.dto.response.UserResponse;
import com.laptopshop.laptopshop.entity.UserEntity;
import com.laptopshop.laptopshop.common.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public  interface UserMapper {
    @Mapping(target = "status", expression = "java(user.getStatus().name())")
    @Mapping(target = "role",expression = "java(user.getRole().name())")
    UserResponse toUserResponse(UserEntity user);

    @Mapping(target = "role", expression = "java(com.laptopshop.laptopshop.common.Role.valueOf(request.getRole()))")
//    @Mapping(target = "createdAt",ignore = true)
//    @Mapping(target = "updateAt",ignore = true)
    UserEntity toUser(UserCreationRequest request);

    @Mapping(target = "role", expression = "java(com.laptopshop.laptopshop.common.Role.valueOf(request.getRole()))")

//    @Mapping(target = "createdAt",ignore = true)
//    @Mapping(target = "updateAt",ignore = true)
    void updateUser(@MappingTarget UserEntity user, UserUpdateRequest request);
}
