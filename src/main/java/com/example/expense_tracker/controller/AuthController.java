package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.*;
import com.example.expense_tracker.service.AuthService;
import com.example.expense_tracker.service.CurrentUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final CurrentUser currentUser;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> login (
        @Valid @RequestBody UserLoginRequest request,
        HttpServletResponse response
    ) {
        UserAndToken userAndToken = authService.login(request, response);
        return ResponseEntity.ok(ApiResponse.success(UserResponse.from(userAndToken.getUser())));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register (
        @Valid @RequestBody UserRegisterRequest request,
        HttpServletResponse response
    ) {
        UserAndToken userAndToken = authService.register(request, response);
        return ResponseEntity.ok(ApiResponse.success(UserResponse.from(userAndToken.getUser())));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout (
        HttpServletRequest request
    ) {
        authService.logout(request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/get-current-user")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser() {
        return ResponseEntity.ok(ApiResponse.success(UserResponse.from(currentUser.getCurrentUser())));
    }
}
