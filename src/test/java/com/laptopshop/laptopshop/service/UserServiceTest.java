package com.laptopshop.laptopshop.service;

import com.laptopshop.laptopshop.common.UserStatus;
import com.laptopshop.laptopshop.dto.request.UserCreationRequest;
import com.laptopshop.laptopshop.dto.request.UserUpdateRequest;
import com.laptopshop.laptopshop.dto.response.UserPageResponse;
import com.laptopshop.laptopshop.dto.response.UserResponse;
import com.laptopshop.laptopshop.entity.UserEntity;
import com.laptopshop.laptopshop.exception.ResourceNotFoundException;
import com.laptopshop.laptopshop.mapper.UserMapper;
import com.laptopshop.laptopshop.repository.RoleRepository;
import com.laptopshop.laptopshop.repository.UserRepository;
import com.laptopshop.laptopshop.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Unit test cho service layer
@ExtendWith(MockitoExtension.class) // Sử dụng Mockito
public class UserServiceTest {

    private UserService userService;

    private @Mock UserRepository userRepository;
    private @Mock PasswordEncoder passwordEncoder;
    private @Mock UserMapper userMapper;
    private @Mock RoleRepository roleRepository;

    private static UserEntity johnDoe;
    private static UserEntity hQuan;

    private static UserResponse johnDoeResponse;
    private static UserResponse hQuanResponse;

    private static UserCreationRequest userRequest;

    @BeforeAll
    static void beforeAll(){
        // Simulated data

        johnDoe = new UserEntity();
        johnDoe.setId(1L);
        johnDoe.setUsername("johnDoe");
        johnDoe.setEmail("johnDoe@gmail.com");
        johnDoe.setPhoneNumber("0345434935");
        johnDoe.setPassword("password");
        johnDoe.setStatus(UserStatus.ACTIVE);

        hQuan = new UserEntity();
        hQuan.setId(2L);
        hQuan.setUsername("hquan");
        hQuan.setEmail("hquan@gmail.com");
        hQuan.setPhoneNumber("0869857917");
        hQuan.setPassword("password");
        hQuan.setStatus(UserStatus.ACTIVE);

        userRequest = UserCreationRequest.builder()
                .password("password")
                .email("johnDoe@gmail.com")
                .username("johnDoe")
                .phoneNumber("0345434935")
                .build();

        johnDoeResponse = UserResponse.builder().id(1L)
                .email("johnDoe@gmail.com")
                .username("johnDoe")
                .phoneNumber("0345434935")
                .build();
        hQuanResponse = UserResponse.builder().id(2L)
                .email("hquan@gmail.com")
                .username("hquan")
                .phoneNumber("0869857917")
                .build();
    }

    @BeforeEach
    void beforeEach(){
        //Initialize the Implementation class
        userService = new UserServiceImpl(userRepository,userMapper, roleRepository, passwordEncoder);
    }

    @Test
    void testGetUserList_Success(){
        //simulate search method
        Page<UserEntity> userPage = new PageImpl<>(List.of(johnDoe,hQuan));
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);
        when(userMapper.toUserResponse(johnDoe)).thenReturn(johnDoeResponse);
        when(userMapper.toUserResponse(hQuan)).thenReturn(hQuanResponse);

        //Call the method to be tested
        UserPageResponse result = userService.findAll(null,null,0,20);

        Assertions.assertNotNull(result);
        assertEquals(2, result.totalElements);
    }

    @Test
    void testGetUserList_Empty() {
        // Giả lập hành vi của UserRepository
        Page<UserEntity> userPage = new PageImpl<>(List.of());
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        // Gọi phương thức cần kiểm tra
        UserPageResponse result = userService.findAll(null, null, 0, 20);

        Assertions.assertNotNull(result);
        assertEquals(0, result.getUsers().size());
    }

    @Test
    void testSearchByKeyword_Success(){
        Page<UserEntity> userPage = new PageImpl<>(List.of(johnDoe,hQuan));
        when(userRepository.searchByKeyword(any(),any(Pageable.class))).thenReturn(userPage);
        when(userMapper.toUserResponse(johnDoe)).thenReturn(johnDoeResponse);
        when(userMapper.toUserResponse(hQuan)).thenReturn(hQuanResponse);

        //Call the method to be tested
        UserPageResponse result = userService.findAll("johnDoe",null,0,20);

        Assertions.assertNotNull(result);
        assertEquals(2, result.totalElements);
        assertEquals("johnDoe", result.getUsers().get(0).getUsername());
    }

    @Test
    void testFindById_Success(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(johnDoe));
        when(userMapper.toUserResponse(johnDoe)).thenReturn(johnDoeResponse);

        //Call the method to be tested
        UserResponse result = userService.findById(1L);

        Assertions.assertNotNull(result);
        assertEquals(1L,result.getId());
    }

    @Test
    void testFindById_Failure(){
        when(userRepository.findById(10L)).thenReturn(Optional.empty());

        // call the method
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> userService.findById(10L));
        assertEquals("User not found", thrown.getMessage());
    }

    @Test
    void testFindByUsername_Success() {
        // Giả lập hành vi của UserRepository
        when(userRepository.findByUsername("johnDoe")).thenReturn(Optional.of(johnDoe));
        when(userMapper.toUserResponse(johnDoe)).thenReturn(johnDoeResponse);

        // Gọi phương thức cần kiểm tra
        UserResponse result = userService.findByUsername("johnDoe");

        Assertions.assertNotNull(result);
        assertEquals("johnDoe", result.getUsername());
    }

    @Test
    void testSave_Success(){
        when(userRepository.save(any(UserEntity.class))).thenReturn(johnDoe);
        when(userMapper.toUser(userRequest)).thenReturn(johnDoe);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // Gọi phương thức cần kiểm tra
        long result = userService.save(userRequest);

        // Kiểm tra kết quả
        Assertions.assertNotNull(result);
        assertEquals(1L, result);

    }

    @Test
    void testUpdate_Success(){
        // simulate entity
        UserEntity updatedUser = new UserEntity();
        johnDoe.setId(1L);
        johnDoe.setUsername("johnDoe");
        johnDoe.setEmail("janesmith@gmail.com");
        johnDoe.setPhoneNumber("0345434935");
        johnDoe.setPassword("password");
        johnDoe.setStatus(UserStatus.ACTIVE);

        UserResponse johnDoe2 = UserResponse.builder().id(1L)
                .email("janesmith@gmail.com")
                .username("johnDoe")
                .phoneNumber("0345434935")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(johnDoe));
        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUser);
        when(userMapper.toUserResponse(johnDoe)).thenReturn(johnDoe2);


        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(1L);
        userUpdateRequest.setUsername("johnDoe");
        userUpdateRequest.setEmail("janesmith@gmail.com");
        userUpdateRequest.setPhoneNumber("0345434935");

        //call method to be tested
        userService.update(userUpdateRequest);


        UserResponse result = userService.findById(1L);

        assertEquals("janesmith@gmail.com", result.getEmail());
    }

    @Test
    void testDeleteUser_Success() {
        // Data preparation
        Long userId = 1L;

        // Giả lập hành vi repository
        when(userRepository.findById(userId)).thenReturn(Optional.of(johnDoe));

        // Gọi phương thức cần kiểm tra
        userService.delete(userId);

        // Kiểm tra kết quả
        assertEquals(UserStatus.INACTIVE, johnDoe.getStatus());
        verify(userRepository, times(1)).save(johnDoe);
    }

}
