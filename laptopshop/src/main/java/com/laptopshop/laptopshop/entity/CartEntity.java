package com.laptopshop.laptopshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carts")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime update_at;
    private int quantity;

    @OneToOne(mappedBy = "cart")
    private UserEntity user;

    @OneToMany(mappedBy = "cart")
    private List<CartDetail> cartDetails;

}
