package com.laptopshop.laptopshop.repository;

import com.laptopshop.laptopshop.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    @Query(value = "select u from UserEntity u where u.status = 'ACTIVE' "+
            "and (lower(u.username) like % :keyword % "+
            "or lower(u.phoneNumber) like % :keyword % "+
            "or lower(u.email) like % :keyword %)")
    Page<UserEntity>searchByKeyword(String keyword, Pageable pageable);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
