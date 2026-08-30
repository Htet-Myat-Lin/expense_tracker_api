package com.example.expense_tracker.service;

import com.example.expense_tracker.dto.UserAndToken;
import com.example.expense_tracker.dto.UserRegisterRequest;
import com.example.expense_tracker.entity.TokenBlacklist;
import com.example.expense_tracker.entity.User;
import com.example.expense_tracker.repository.TokenBlacklistRepository;
import com.example.expense_tracker.repository.UserRepository;
import com.example.expense_tracker.dto.UserLoginRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistRepository tokenBlacklistRepository;

    @Value("${jwt.expiration}")
    private Long expiration;

    public UserAndToken login (
        UserLoginRequest req,
        HttpServletResponse res
    ) {
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(() -> new BadCredentialsException("Invalid Email or Password"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        return this.generateTokensAndSetCookie(user, res);
    }

    public UserAndToken register (
        UserRegisterRequest req,
        HttpServletResponse res
    ) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new BadCredentialsException("Email already exists");
        }
        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .build();
        User newUser = userRepository.save(user);
        return this.generateTokensAndSetCookie(newUser, res);
    }

    public UserAndToken generateTokensAndSetCookie (
            User user,
            HttpServletResponse res
    ) {
        String token = jwtService.generateToken(user.getId());
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .maxAge(expiration)
                .build();
        res.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return UserAndToken.builder()
                .token(token)
                .user(user)
                .build();
    }

    public void logout (HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                tokenBlacklistRepository.save(
                    TokenBlacklist.builder()
                        .token(cookie.getValue())
                        .build()
                );
            }
        }
    }
}
