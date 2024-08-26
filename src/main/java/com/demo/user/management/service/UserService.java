package com.demo.user.management.service;

import com.demo.user.management.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();

    List<User> getAllUsers();
    User getUser();
    User getUser(Long id);
}
