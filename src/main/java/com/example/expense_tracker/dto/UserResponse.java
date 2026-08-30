package com.example.expense_tracker.dto;

import com.example.expense_tracker.entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class UserResponse {
    private String id;
    private String email;
    private String name;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getUsername())
                .build();
    }
}
