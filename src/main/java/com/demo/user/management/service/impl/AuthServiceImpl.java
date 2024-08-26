package com.demo.user.management.service.impl;

import com.demo.user.management.dto.JwtResponse;
import com.demo.user.management.dto.RefreshTokenRequest;
import com.demo.user.management.dto.SignInRequest;
import com.demo.user.management.dto.SignUpRequest;
import com.demo.user.management.entity.Role;
import com.demo.user.management.entity.User;
import com.demo.user.management.repo.UserRepository;
import com.demo.user.management.service.AuthService;
import com.demo.user.management.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.ZonedDateTime;
import java.util.HashMap;


@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public User signUp(SignUpRequest signUpRequest) {
        log.info("AuthServiceImpl signUp");
        Boolean usernameExists = userRepository.existsByUsername(signUpRequest.getUsername());
        if (usernameExists) {
            String error = String.format("Username : %s exits.", signUpRequest.getUsername());
            throw new InvalidParameterException(error);
        }
        Boolean emailExists = userRepository.existsByEmail(signUpRequest.getEmail());
        if (emailExists) {
            String error = String.format("Email : %s exits.", signUpRequest.getEmail());
            throw new InvalidParameterException(error);
        }
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setCreatedTime(ZonedDateTime.now());
        return userRepository.save(user);
    }


    @Override
    public JwtResponse signIn(SignInRequest signInRequest) {
        log.info("AuthServiceImpl signIn");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));

        var user = userRepository.findByUsername(signInRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        return new JwtResponse(jwt, refreshToken);
    }


    @Override
    public JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userName = jwtService.extractUserName(refreshTokenRequest.getToken());
        var user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);
            return new JwtResponse(jwt, refreshTokenRequest.getToken());
        }
        throw new IllegalArgumentException("Invalid token");
    }

}
