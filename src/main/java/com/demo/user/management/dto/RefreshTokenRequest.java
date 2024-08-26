package com.demo.user.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String token;
}
