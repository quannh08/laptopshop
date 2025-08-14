package com.laptopshop.laptopshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AbstractEntity<Integer>{
    private String name;
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Permission> permissions;
}
