package com.demo.user.management.service;

import com.demo.user.management.dto.UpdateUserRequest;
import com.demo.user.management.dto.UserDto;
import com.demo.user.management.dto.RoleRequest;
import com.demo.user.management.entity.UserStatus;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();

    List<UserDto> getAllUsers();
    UserDto getUser();
    UserDto getUserById(Long id);

    List<UserDto> getUsersWithStatus(UserStatus status);

    void approveUserStatus(Long id);

    void deleteUser(Long id);

    UserDto updateByUser(UpdateUserRequest request);

    UserDto assignRole(RoleRequest roleRequest);
}
