package com.laptopshop.laptopshop.repository;

import com.laptopshop.laptopshop.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    @Query("SELECT p FROM Permission p WHERE p.name IN :names")
    List<Permission> findAllByName(@Param("names") Set<String> names);

    void deleteByName(String permission);
}
