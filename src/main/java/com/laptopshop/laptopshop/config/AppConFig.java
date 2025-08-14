package com.laptopshop.laptopshop.config;


import com.laptopshop.laptopshop.common.UserStatus;
import com.laptopshop.laptopshop.entity.Role;
import com.laptopshop.laptopshop.entity.UserEntity;
import com.laptopshop.laptopshop.repository.RoleRepository;
import com.laptopshop.laptopshop.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AppConFig {
    PasswordEncoder passwordEncoder;

    RoleRepository roleRepository;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            initializeRoles();

            // Tạo tài khoản admin nếu chưa tồn tại
            if (userRepository.findByUsername("admin").isEmpty()) {
                Role adminRole = roleRepository.findByName("ADMIN")
                        .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));
                log.info(adminRole.getName());

                    UserEntity user = UserEntity.builder()
                            .username("admin")
                            .password(passwordEncoder.encode("admin123"))
                            .roles(new HashSet<>(Collections.singletonList(adminRole)))
                            .status(UserStatus.ACTIVE)
                            .build();

                    userRepository.save(user);
                    log.warn("admin user has been created with default password: admin, please change it");

            }
            ;
        };
    }

    private void initializeRoles() {
        log.info("initialize role");
            List<String> defaultRoles = Arrays.asList("ADMIN", "USER");
            for (String roleName : defaultRoles) {
                if (roleRepository.findByName(roleName).isEmpty()) {
                    Role role = new Role();
                    role.setName(roleName);
                    roleRepository.save(role);
                    log.info("Created role: {}", roleName);
                }
            }
        }

}
