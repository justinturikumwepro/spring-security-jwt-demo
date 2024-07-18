package com.softech.spring_security_jwt_demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softech.spring_security_jwt_demo.model.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    Optional<AppRole> findByRole(String roleName);
}
