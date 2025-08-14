package com.laptopshop.laptopshop.dto.request;

import jakarta.annotation.Nullable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest implements Serializable {
    private Long id;

    @Size(min = 5, message = "must be more than or equal to 5 characters")
    private String username;

    @Email(message = "Email Invalid")
    private String email;
    private String phoneNumber;
    private Set<String> roles;
}
