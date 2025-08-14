package com.laptopshop.laptopshop.service.Impl;

import com.laptopshop.laptopshop.dto.request.PermissionRequest;
import com.laptopshop.laptopshop.dto.response.PermissionResponse;
import com.laptopshop.laptopshop.entity.Permission;
import com.laptopshop.laptopshop.mapper.PermissionMapper;
import com.laptopshop.laptopshop.repository.PermissionRepository;
import com.laptopshop.laptopshop.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse create(PermissionRequest request){
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAll(){
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @Override
    public void delete(String permission){
        permissionRepository.deleteByName(permission);
    }
}
