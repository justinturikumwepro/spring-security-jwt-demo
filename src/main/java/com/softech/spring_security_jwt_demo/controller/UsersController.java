package com.softech.spring_security_jwt_demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softech.spring_security_jwt_demo.model.AppUser;
import com.softech.spring_security_jwt_demo.repository.AppUserRepository;

import lombok.AllArgsConstructor;

@RequestMapping("/api/users")
@RestController
@AllArgsConstructor
public class UsersController {

    private final AppUserRepository appUserRepository;

    @GetMapping("/")
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

}
