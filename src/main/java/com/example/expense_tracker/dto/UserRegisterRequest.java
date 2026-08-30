package com.example.expense_tracker.dto;

import jakarta.validation.constraints.Email;
import com.example.expense_tracker.validation.PasswordMatches;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@PasswordMatches
public class UserRegisterRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Confirm Password is required")
    @Size(min = 8, message = "Confirm Password must be at least 8 characters long")
    private String confirmPassword;
}
