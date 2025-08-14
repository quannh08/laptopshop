package com.laptopshop.laptopshop.repository;

import com.laptopshop.laptopshop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    List<Role> findAllByNameIn(Set<String> names);

    Optional<Role> findByName(String roleName);
}

