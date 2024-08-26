package com.demo.user.management.controller;

import com.demo.user.management.entity.User;
import com.demo.user.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    @GetMapping("/ping")
    public String ping(){
        return "user-pong";
    }

    @GetMapping("/profile")
    public User getUser() {
        return userService.getUser();
    }
}
