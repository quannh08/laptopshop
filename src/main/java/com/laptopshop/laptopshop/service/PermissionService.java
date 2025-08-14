package com.laptopshop.laptopshop.service;

import com.laptopshop.laptopshop.dto.request.PermissionRequest;
import com.laptopshop.laptopshop.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {

    public PermissionResponse create(PermissionRequest request);

    public List<PermissionResponse> getAll();

    public void delete(String permission);
}
