package com.laptopshop.laptopshop.service;

import com.laptopshop.laptopshop.dto.request.RoleRequest;
import com.laptopshop.laptopshop.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {

    public RoleResponse create(RoleRequest request);

    public List<RoleResponse> getAll();

    public void delete(String role);
}
