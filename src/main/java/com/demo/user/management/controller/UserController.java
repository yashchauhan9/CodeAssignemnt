package com.demo.user.management.controller;

import com.demo.user.management.dto.UpdateUserRequest;
import com.demo.user.management.dto.UserDto;
import com.demo.user.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    @GetMapping("/profile")
    public UserDto getUser() {
        return userService.getUser();
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateByUser(request));
    }
}
