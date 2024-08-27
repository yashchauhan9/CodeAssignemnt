package com.demo.user.management.controller;

import com.demo.user.management.dto.UserDto;
import com.demo.user.management.dto.RoleRequest;
import com.demo.user.management.entity.UserStatus;
import com.demo.user.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/users/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsersWithStatus(@RequestParam UserStatus status) {
        return ResponseEntity.ok(userService.getUsersWithStatus(status));
    }

    @PutMapping("/users/approve/{id}")
    public ResponseEntity<?> approveUser(@PathVariable Long id) {
        userService.approveUserStatus(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/role")
    public ResponseEntity<?> assignRole(@RequestBody RoleRequest roleRequest) {
        return ResponseEntity.ok(userService.assignRole(roleRequest));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }


}
