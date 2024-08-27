package com.demo.user.management.dto;

import com.demo.user.management.entity.Role;
import com.demo.user.management.entity.UserStatus;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String name;
    private String email;
    private ZonedDateTime createdTime;
    private ZonedDateTime lastModifiedTime;
    private List<Role> roles;
    private String lastModifiedBy;
    private UserStatus status;
    private String details;

}
