package com.softech.spring_security_jwt_demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softech.spring_security_jwt_demo.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

    Boolean existsByUsername(String username);
}
