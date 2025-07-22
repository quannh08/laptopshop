package com.laptopshop.laptopshop.dto.response;

import com.laptopshop.laptopshop.common.Role;
import com.laptopshop.laptopshop.common.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String status;
    private String role;
}
