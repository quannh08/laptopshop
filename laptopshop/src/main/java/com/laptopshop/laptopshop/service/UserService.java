package com.laptopshop.laptopshop.service;

import com.laptopshop.laptopshop.dto.request.UserCreationRequest;
import com.laptopshop.laptopshop.dto.request.UserUpdateRequest;
import com.laptopshop.laptopshop.dto.response.UserPageResponse;
import com.laptopshop.laptopshop.dto.response.UserResponse;

public interface UserService {
    UserPageResponse findAll(String keyword, String sort, int page, int size);

    UserResponse findById(Long id);

    UserResponse findByUsername(String username);

    UserResponse findByEmail(String email);

    UserResponse getMyInfo();

    long save(UserCreationRequest request);

    void update(UserUpdateRequest request);

    void delete(Long id);
}
