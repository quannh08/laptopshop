package com.laptopshop.laptopshop.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.laptopshop.laptopshop.common.Role;
import com.laptopshop.laptopshop.common.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    private Set<RoleResponse> roles;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime updatedAt;
}
