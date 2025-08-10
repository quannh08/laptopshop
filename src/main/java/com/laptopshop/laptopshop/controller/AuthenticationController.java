package com.laptopshop.laptopshop.controller;

import com.laptopshop.laptopshop.dto.request.*;
import com.laptopshop.laptopshop.dto.response.AuthenticationResponse;
import com.laptopshop.laptopshop.dto.response.IntrospectResponse;
import com.laptopshop.laptopshop.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "AUTHENTICATION CONTROLLER")
@RequestMapping("/auth")
@Validated
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest request) {
        var res = authenticationService.registerUser(request);

        Map<String,Object> result = new LinkedHashMap<>();

        if (res.equals("success")){
            result.put("status",HttpStatus.CREATED.value());
            result.put("message","Register successfully");
            result.put("data", res);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        result.put("status",HttpStatus.CONFLICT.value());
        result.put("message","Register failly");
        result.put("data", res);
        return new ResponseEntity<>(result, HttpStatus.CONFLICT);

    }

    @PostMapping("/introspect")
    public ResponseEntity<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);

        Map<String,Object> result = new LinkedHashMap<>();

        result.put("status",HttpStatus.ACCEPTED.value());
        result.put("message","Logout");
        result.put("data", "");

        return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> authenticate(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {

        Map<String,Object> result = new LinkedHashMap<>();

        result.put("status",HttpStatus.ACCEPTED.value());
        result.put("message","Refresh token");
        result.put("data", authenticationService.refreshToken(request));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
