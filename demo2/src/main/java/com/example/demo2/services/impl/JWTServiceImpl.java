package com.example.demo2.services.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo2.services.JWTConstants;
import com.example.demo2.services.SecretKeyGenerator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JWTServiceImpl {

    private SecretKeyGenerator secretKeyGenerator;

    public String generateRefreshToken(
            UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, JWTConstants.REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public String generateRefreshToken(
            String username) {
        return buildToken(username, JWTConstants.REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateAccessToken(new HashMap<>(), userDetails);
    }

    public String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, JWTConstants.ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String generateAccessToken(String username) {
        return buildToken(username, JWTConstants.ACCESS_TOKEN_EXPIRATION_TIME);
    }

    private String buildToken(String username, Long expiration) {

        SecretKey key = secretKeyGenerator.generateAPISecretKey();
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    private String buildToken(Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration) {

        SecretKey key = secretKeyGenerator.generateAPISecretKey();
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractLogin(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractLogin(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        return secretKeyGenerator.generateAPISecretKey();
    }
   

}
