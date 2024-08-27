package com.demo.user.management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotEmpty
    private String username;

    @NotEmpty
    private String name;

    @Email
    private String email;

    @NotEmpty
    private String password;
}
