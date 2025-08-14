package com.laptopshop.laptopshop.entity;

import com.laptopshop.laptopshop.common.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends AbstractEntity<Long>{

    private String username;
    private String password;
    private String email;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",length = 255)
    private UserStatus status;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "role")
//    private Role role;

    @ManyToMany
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private CartEntity cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<OrderEntity> orders;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<ReviewEntity> reviews;

}
