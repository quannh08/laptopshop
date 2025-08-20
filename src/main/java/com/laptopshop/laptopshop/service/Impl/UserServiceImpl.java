package com.laptopshop.laptopshop.service.Impl;

import com.laptopshop.laptopshop.common.Role;
import com.laptopshop.laptopshop.common.UserStatus;
import com.laptopshop.laptopshop.dto.request.UserCreationRequest;
import com.laptopshop.laptopshop.dto.request.UserUpdateRequest;
import com.laptopshop.laptopshop.dto.response.UserPageResponse;
import com.laptopshop.laptopshop.dto.response.UserResponse;
import com.laptopshop.laptopshop.entity.UserEntity;
import com.laptopshop.laptopshop.exception.InvalidDataException;
import com.laptopshop.laptopshop.exception.ResourceNotFoundException;
import com.laptopshop.laptopshop.mapper.UserMapper;
import com.laptopshop.laptopshop.repository.RoleRepository;
import com.laptopshop.laptopshop.repository.UserRepository;
import com.laptopshop.laptopshop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserPageResponse findAll(String keyword, String sort, int page, int size) {
        log.info("find all start");
        //sorting
        Sort.Order order =new Sort.Order(Sort.Direction.ASC,"id");
        if(StringUtils.hasLength(sort)){
            Pattern pattern =Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher =pattern.matcher(sort);
            if (matcher.find()) {
                String columnName = matcher.group(1);
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    order = new Sort.Order(Sort.Direction.ASC, columnName);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, columnName);
                }
            }
        }
        // Xu ly truong hop FE muon bat dau voi page = 1
        int pageNo = 0;
        if (page > 0) {
            pageNo = page - 1;
        }
        //Paging
        Pageable pageable = PageRequest.of(pageNo,size,Sort.by(order));
        Page<UserEntity> entityPage;

        if(StringUtils.hasLength(keyword)){
            log.info("search by keyword: {}",keyword);
            keyword = "%"+keyword+"%";
            entityPage = userRepository.searchByKeyword(keyword, pageable);
        }
        else{
            entityPage = userRepository.findAll(pageable);
        }

        return getUserPageResponse(page,size,entityPage);
    }

    @Override
    public UserResponse findById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse findByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse findByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        UserEntity user = userRepository.findByUsername(name).orElseThrow(
                () -> new ResourceNotFoundException("User not existed"));
        return userMapper.toUserResponse(user);
    }

    @Override
    public long save(UserCreationRequest request) {
        log.info("Saving user: {}",request);

        Optional<UserEntity> userByUsername = userRepository.findByUsername(request.getUsername());
        if(userByUsername.isPresent()){
            throw new InvalidDataException("Username already exists");
        }
        var roles = roleRepository.findAllByNameIn(request.getRoles());

        UserEntity user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRoles(new HashSet<>(roles));
        userRepository.save(user);

        log.info("Save user {}",user);
        return user.getId();
    }

    @Override
    public void update(UserUpdateRequest request) {
        log.info("Update User: {}", request);
        UserEntity user = getUserEntity(request.getId());

        var roleStr = request.getRoles(); // <-- change here
        log.info("Role from request: {}", roleStr);
        if(roleStr != null)
            user.setRoles(new HashSet<>(roleRepository.findAllByNameIn(request.getRoles())));

        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPhoneNumber(request.getPhoneNumber());


        log.info("Updated user: {}", user);
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user: {}", id);

        UserEntity user = getUserEntity(id);
        user.setStatus(UserStatus.INACTIVE);

        userRepository.save(user);
        log.info("Deleted user id: {}", id);
    }


    /**
     * Get user by id
     *
     * @param id
     * @return
     */
    private UserEntity getUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    /**
     * Convert Userto UserResponse
     *
     * @param page
     * @param size
     * @param users
     * @return
     */
    private UserPageResponse getUserPageResponse(int page, int size, Page<UserEntity> users) {
        log.info("Convert User Page");

        List<UserResponse> userResponseList = users.stream().map(userMapper::toUserResponse).toList();

        UserPageResponse response =new UserPageResponse();
        response.setPageNumber(page);
        response.setPageSize(size);
        response.setTotalElements(users.getTotalElements());
        response.setTotalPages(users.getTotalPages());
        response.setUsers(userResponseList);
        return response;
    }
}
