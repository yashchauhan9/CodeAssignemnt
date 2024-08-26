package com.demo.user.management.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String name;
    private String email;
    private String password;
}
