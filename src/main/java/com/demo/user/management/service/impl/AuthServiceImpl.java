package com.demo.user.management.service.impl;

import com.demo.user.management.dto.*;
import com.demo.user.management.entity.*;
import com.demo.user.management.mapper.UserMapper;
import com.demo.user.management.repo.UserRepository;
import com.demo.user.management.service.AuditService;
import com.demo.user.management.service.AuthService;
import com.demo.user.management.service.JwtService;
import com.demo.user.management.service.ObjectMapperUtil;
import io.jsonwebtoken.JwtException;
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

    private final UserMapper userMapper;

    private final AuditService auditService;

    public UserDto signUp(SignUpRequest signUpRequest) {
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
        user.setStatus(UserStatus.PENDING);
        user.setCreatedTime(ZonedDateTime.now());
        user.setLastModifiedTime(ZonedDateTime.now());
        user.setLastModifiedBy(signUpRequest.getUsername());
        User savedUser = userRepository.save(user);
        auditService.audit(new Audit(AuditType.CREATE, savedUser.getId(), null, ObjectMapperUtil.toJsonString(savedUser)));
        return userMapper.userToUserDTO(user);
    }


    @Override
    public JwtResponse signIn(SignInRequest signInRequest) {
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
        throw new JwtException("Invalid token");
    }

}
