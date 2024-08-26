package com.demo.user.management.dto;

import com.demo.user.management.entity.Entitlement;
import com.demo.user.management.entity.Role;
import com.demo.user.management.entity.UserStatus;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String name;
    private String email;
    private ZonedDateTime createdTime;
    private ZonedDateTime lastModifiedTime;
    private Entitlement entitlement;
    private Role role;
    private String lastModifiedBy;
    private UserStatus status;
    private String details;

}
