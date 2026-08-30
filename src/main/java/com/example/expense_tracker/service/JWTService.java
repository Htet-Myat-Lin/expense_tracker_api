package com.example.expense_tracker.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(this.secretKey.getBytes());
    }

    public String generateToken(String id) {
        return Jwts.builder()
                .subject(id)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + this.expiration))
                .signWith(this.getKey())
                .compact();
    }

    private Claims getAllClaims(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(this.getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Date getTokenExpiration(String token) throws JwtException {
        return this.getAllClaims(token).getExpiration();
    }

    public boolean isTokenValid(String token) {
        try {
            return !this.getTokenExpiration(token).before(new Date());
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractUserId (String token) throws JwtException {
        Claims claims = this.getAllClaims(token);
        return claims.getSubject();
    }
}
