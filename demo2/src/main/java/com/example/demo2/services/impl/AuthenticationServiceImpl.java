package com.example.demo2.services.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo2.dto.JwtAuthenticationResponse;
import com.example.demo2.dto.RefreshTokenRequest;
import com.example.demo2.dto.SignUpRequest;
import com.example.demo2.dto.SigninRequest;
import com.example.demo2.models.Roles;
import com.example.demo2.models.User;
import com.example.demo2.repositories.UserRepo;
import com.example.demo2.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    
    private final UserRepo userRepo;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JWTServiceImpl jwtService;

    public User signup(SignUpRequest signUpRequest){
        User user = new User();

        user.setEmail(signUpRequest.getEmail());
        user.setRole(Roles.TEACHER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        return userRepo.save(user);
    }

    public JwtAuthenticationResponse signin(SigninRequest signinRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
    
        User user = userRepo.findByEmail(signinRequest.getEmail()).orElseThrow(()-> new IllegalArgumentException("Invalid email or password"));

        var jwt = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user.getUsername());
        
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken((String) refreshToken);
        return jwtAuthenticationResponse;

    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtService.extractLogin(refreshTokenRequest.getToken());

        User user = userRepo.findByEmail(userEmail)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt = jwtService.generateAccessToken(user);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;

        
    }
    
}
