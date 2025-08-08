package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.request.UserCreationRequest;
import com.laptopshop.laptopshop.dto.request.UserUpdateRequest;
import com.laptopshop.laptopshop.dto.response.UserResponse;
import com.laptopshop.laptopshop.entity.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-08T12:59:19+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse toUserResponse(UserEntity user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.username( user.getUsername() );
        userResponse.password( user.getPassword() );
        userResponse.email( user.getEmail() );
        userResponse.phoneNumber( user.getPhoneNumber() );
        userResponse.createdAt( user.getCreatedAt() );
        userResponse.updatedAt( user.getUpdatedAt() );

        userResponse.status( user.getStatus().name() );
        userResponse.role( user.getRole().name() );

        return userResponse.build();
    }

    @Override
    public UserEntity toUser(UserCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.username( request.getUsername() );
        userEntity.password( request.getPassword() );
        userEntity.email( request.getEmail() );
        userEntity.phoneNumber( request.getPhoneNumber() );

        userEntity.role( com.laptopshop.laptopshop.common.Role.valueOf(request.getRole()) );

        return userEntity.build();
    }

    @Override
    public void updateUser(UserEntity user, UserUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        user.setId( request.getId() );
        user.setUsername( request.getUsername() );
        user.setEmail( request.getEmail() );
        user.setPhoneNumber( request.getPhoneNumber() );

        user.setRole( com.laptopshop.laptopshop.common.Role.valueOf(request.getRole()) );
    }
}
