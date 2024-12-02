package com.example.demo2.dto;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    
    private String token;

    private String refreshToken;
}
