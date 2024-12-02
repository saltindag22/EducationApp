package com.example.demo2.services;

import com.example.demo2.dto.JwtAuthenticationResponse;
import com.example.demo2.dto.RefreshTokenRequest;
import com.example.demo2.dto.SignUpRequest;
import com.example.demo2.dto.SigninRequest;
import com.example.demo2.models.User;

public interface AuthenticationService {
    
    User signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SigninRequest signinRequest);


    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
