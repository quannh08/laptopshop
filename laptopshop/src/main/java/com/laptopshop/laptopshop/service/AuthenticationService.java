package com.laptopshop.laptopshop.service;

import com.laptopshop.laptopshop.dto.request.AuthenticationRequest;
import com.laptopshop.laptopshop.dto.request.IntrospectRequest;
import com.laptopshop.laptopshop.dto.request.RegisterRequest;
import com.laptopshop.laptopshop.dto.response.AuthenticationResponse;
import com.laptopshop.laptopshop.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;
public interface AuthenticationService {
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    public AuthenticationResponse authenticate(AuthenticationRequest request);

    public String registerUser(RegisterRequest registerRequest);
}
