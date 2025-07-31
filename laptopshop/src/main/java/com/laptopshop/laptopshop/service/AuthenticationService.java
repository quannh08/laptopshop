package com.laptopshop.laptopshop.service;

import com.laptopshop.laptopshop.dto.request.*;
import com.laptopshop.laptopshop.dto.response.AuthenticationResponse;
import com.laptopshop.laptopshop.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;
public interface AuthenticationService {
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    public AuthenticationResponse authenticate(AuthenticationRequest request);

    public String registerUser(RegisterRequest registerRequest);

    public void logout(LogoutRequest request) throws ParseException, JOSEException;

    public AuthenticationResponse refreshToken(RefreshRequest request)throws ParseException, JOSEException;
}
