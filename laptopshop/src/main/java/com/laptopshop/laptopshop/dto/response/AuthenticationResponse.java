package com.laptopshop.laptopshop.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AuthenticationResponse implements Serializable {
    private String token;
    private Long userId;
    private boolean authenticated;
}
