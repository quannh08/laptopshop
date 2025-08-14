package com.laptopshop.laptopshop.service.Impl;

import com.laptopshop.laptopshop.common.UserStatus;
import com.laptopshop.laptopshop.dto.request.*;
import com.laptopshop.laptopshop.dto.response.AuthenticationResponse;
import com.laptopshop.laptopshop.dto.response.IntrospectResponse;
import com.laptopshop.laptopshop.dto.response.UserResponse;
import com.laptopshop.laptopshop.entity.InvalidatedToken;
import com.laptopshop.laptopshop.entity.Role;
import com.laptopshop.laptopshop.entity.UserEntity;
import com.laptopshop.laptopshop.exception.ResourceNotFoundException;
import com.laptopshop.laptopshop.exception.UnauthenticateException;
import com.laptopshop.laptopshop.repository.InvalidatedTokenRepository;
import com.laptopshop.laptopshop.repository.RoleRepository;
import com.laptopshop.laptopshop.repository.UserRepository;
import com.laptopshop.laptopshop.service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final InvalidatedTokenRepository invalidatedTokenRepository;

    private final RoleRepository roleRepository;

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Override
    public IntrospectResponse introspect(IntrospectRequest request)
            throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token);
        } catch (Exception e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean authenticated = passwordEncoder.matches(request.getPassword(),
                user.getPassword());

        log.info(user.getRoles().toString());
        if (!authenticated)
            throw new AuthenticationException("You are not logged in.") {
            };
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .authenticated(true)
                .build();
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request)
            throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken());

        String type = (String) signedJWT.getJWTClaimsSet().getClaim("type");

        // Kiểm tra đúng loại token
        if (!"refresh_token".equals(type)) {
            throw new UnauthenticateException("Unauthentication");
        }

        // Nếu bị thu hồi
        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new UnauthenticateException("Unauthentication");
        }

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();

        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("user not existed")
        );

        var newAccessToken = generateAccessToken(user);
        var newRefreshToken = generateRefreshToken(user);

        var token = generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .authenticated(true)
                .build();
    }

    @Override
    public String registerUser(RegisterRequest registrationRequest) {
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            return "Username is already taken";
        }

        // Check if email already exists
        if (registrationRequest.getEmail() != null &&
                userRepository.existsByEmail(registrationRequest.getEmail())) {
            return "Email is already in use";
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

        UserEntity user = UserEntity.builder()
                .username(registrationRequest.getUsername())
                .email(registrationRequest.getEmail())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .status(UserStatus.ACTIVE)
                .roles(new HashSet<>(Collections.singletonList(userRole)))
                .build();

        userRepository.save(user);
        log.info("Save user {}", user);

        return "success";
    }

    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        var signToken = verifyToken(request.getToken());

        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);
    }

    private String generateAccessToken(UserEntity user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("laptopshop.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(7, ChronoUnit.HOURS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    private String generateRefreshToken(UserEntity user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("laptopShop.com")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)))
                .jwtID(UUID.randomUUID().toString())
                .claim("type", "refresh_token")
                .build();

        Payload payload = new Payload(claims.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date())))
            throw new UnauthenticateException("Token expired");

        if (invalidatedTokenRepository
                .existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new UnauthenticateException("Unauthetication");

        return signedJWT;
    }

    public static Optional<String> getCurrentUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return Optional.of(authentication.getName());
        }
        return Optional.empty();
    }

    private String buildScope(UserEntity user){
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions()
                            .forEach(permission -> stringJoiner.add(permission.getName()));
            });

        return stringJoiner.toString();
    }
}