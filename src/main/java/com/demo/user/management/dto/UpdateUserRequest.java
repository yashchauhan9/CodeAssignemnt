package com.demo.user.management.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String details;
}
