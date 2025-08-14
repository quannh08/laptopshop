package com.laptopshop.laptopshop.service.Impl;

import com.laptopshop.laptopshop.dto.request.RoleRequest;
import com.laptopshop.laptopshop.dto.response.RoleResponse;
import com.laptopshop.laptopshop.mapper.RoleMapper;
import com.laptopshop.laptopshop.repository.PermissionRepository;
import com.laptopshop.laptopshop.repository.RoleRepository;
import com.laptopshop.laptopshop.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @Override
    public RoleResponse create(RoleRequest request){
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllByName(request.getPermissions());
        role.setPermissions(new HashSet<>((Collection) permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> getAll(){
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    @Override
    public void delete(String role){
        roleRepository.deleteById(role);
    }
}
