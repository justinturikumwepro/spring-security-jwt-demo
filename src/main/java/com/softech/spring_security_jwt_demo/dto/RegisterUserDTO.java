package com.softech.spring_security_jwt_demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterUserDTO {
    private String username;
    private String password;
    private String role;

}
