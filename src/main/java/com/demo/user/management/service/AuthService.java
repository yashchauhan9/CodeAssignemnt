package com.demo.user.management.service;

import com.demo.user.management.dto.JwtResponse;
import com.demo.user.management.dto.RefreshTokenRequest;
import com.demo.user.management.dto.SignInRequest;
import com.demo.user.management.dto.SignUpRequest;
import com.demo.user.management.dto.UserDto;

public interface AuthService {
    UserDto signUp(SignUpRequest signUpRequest);

    JwtResponse signIn(SignInRequest signInRequest);

    JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
