package com.softech.spring_security_jwt_demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginDTO {

    private String username;
    private String password;
}
