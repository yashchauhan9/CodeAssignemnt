package com.demo.user.management.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {

    String generateToken(UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> claims, UserDetails userDetails);

    String extractUserName(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

}
