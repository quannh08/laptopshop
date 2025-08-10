package com.laptopshop.laptopshop.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordRequest implements Serializable {
    private Long id;

    private String oldPassword;

    @Size(min = 6, message = "must be more than or equal to 6 characters")
    private String newPassword;

    private String confirmPassword;
}
