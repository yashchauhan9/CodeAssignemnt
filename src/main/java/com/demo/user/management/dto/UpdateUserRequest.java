package com.demo.user.management.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @NotEmpty
    private String name;

    private String details;
}
