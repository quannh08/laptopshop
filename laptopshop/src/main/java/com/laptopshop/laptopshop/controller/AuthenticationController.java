package com.laptopshop.laptopshop.controller;

import com.laptopshop.laptopshop.dto.request.AuthenticationRequest;
import com.laptopshop.laptopshop.dto.request.IntrospectRequest;
import com.laptopshop.laptopshop.dto.request.RegisterRequest;
import com.laptopshop.laptopshop.dto.response.AuthenticationResponse;
import com.laptopshop.laptopshop.dto.response.IntrospectResponse;
import com.laptopshop.laptopshop.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
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
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
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


}
