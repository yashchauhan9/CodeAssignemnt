package com.demo.user.management.dto;

import com.demo.user.management.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data public class RoleRequest {
    @NotNull private Long userId;
    @NotNull private Role role;
    private String details;

}
