package com.laptopshop.laptopshop.controller;

import com.laptopshop.laptopshop.dto.response.UserPageResponse;
import com.laptopshop.laptopshop.dto.response.UserResponse;
import com.laptopshop.laptopshop.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.security.test.context.support.WithMockUser;



import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static UserResponse johnDoeResponse;
    private static UserResponse hQuanResponse;

    @BeforeAll
    static void setUp(){
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

    @Test
    @WithMockUser(authorities = {"admin", "manager"})
    void getUserListTest() throws Exception{
        List<UserResponse> userListResponses = List.of(johnDoeResponse,hQuanResponse);

        UserPageResponse userPageResponse = new UserPageResponse();
        userPageResponse.setPageNumber(0);
        userPageResponse.setPageSize(20);
        userPageResponse.setTotalPages(1);
        userPageResponse.setTotalElements(2);
        userPageResponse.setUsers(userListResponses);

        Mockito.when(userService.findAll(null, null, 0, 20)).thenReturn(userPageResponse);

        mockMvc.perform(get("/user/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(200)))
                .andExpect(jsonPath("$.message", is("User list")))
                .andExpect(jsonPath("$.data.totalPages", is(1)))
                .andExpect(jsonPath("$.data.totalElements", is(2)))
                .andExpect(jsonPath("$.data.users[0].id", is(1)))
                .andExpect(jsonPath("$.data.users[0].username", is("johnDoe")));
    }

}
