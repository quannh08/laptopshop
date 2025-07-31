package com.laptopshop.laptopshop.dto.request;

import jakarta.validation.constraints.Email;
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
public class RegisterRequest implements Serializable {
    @Size(min = 5, message = "must be more than or equal to 4 characters")
    private String username;
    @Size(min = 6, message = "must be more than or equal to 6 characters")
    private String password;
    @Email(message = "Email invalid")
    private String email;
    private String phoneNumber;
}
