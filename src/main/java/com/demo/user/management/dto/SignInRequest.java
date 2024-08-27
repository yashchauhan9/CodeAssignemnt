package com.demo.user.management.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignInRequest {
    @NotNull private String username;
    @NotNull private String password;
}
