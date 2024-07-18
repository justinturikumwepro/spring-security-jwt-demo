package com.softech.spring_security_jwt_demo.controller;

import java.util.Collections;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softech.spring_security_jwt_demo.dto.LoginDTO;
import com.softech.spring_security_jwt_demo.dto.RegisterUserDTO;
import com.softech.spring_security_jwt_demo.model.AppRole;
import com.softech.spring_security_jwt_demo.model.AppUser;
import com.softech.spring_security_jwt_demo.repository.AppRoleRepository;
import com.softech.spring_security_jwt_demo.repository.AppUserRepository;
import com.softech.spring_security_jwt_demo.security.JwtGenerator;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        if (registerUserDTO.getUsername() == null || registerUserDTO.getPassword() == null
                || registerUserDTO.getRole() == null) {
            return ResponseEntity.badRequest().body("Error: Username, password and role are required!");
        }
        if (appUserRepository.existsByUsername(registerUserDTO.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        AppUser appUser = new AppUser();
        appUser.setUsername(registerUserDTO.getUsername());
        appUser.setPassword(passwordEncoder.encode((registerUserDTO.getPassword())));

        Optional<AppRole> roles = appRoleRepository.findByRole(registerUserDTO.getRole());
        var role = roles.orElseGet(() -> {
            AppRole newRole = new AppRole();
            newRole.setRole(registerUserDTO.getRole());
            return appRoleRepository.save(newRole);
        });

        appUser.setRoles(Collections.singletonList(role));
        appUserRepository.save(appUser);

        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    private ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String response = jwtGenerator.generateToken(authentication);
        return ResponseEntity.ok().body(response);
    }
}
