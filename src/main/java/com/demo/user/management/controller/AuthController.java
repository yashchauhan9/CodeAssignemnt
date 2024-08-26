package com.demo.user.management.controller;

import com.demo.user.management.dto.JwtResponse;
import com.demo.user.management.dto.RefreshTokenRequest;
import com.demo.user.management.dto.SignInRequest;
import com.demo.user.management.dto.SignUpRequest;
import com.demo.user.management.entity.User;
import com.demo.user.management.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<User> singUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> singUp(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> singUp(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
}
