package com.example.expense_tracker.dto;

import com.example.expense_tracker.entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserAndToken {
    private String token;
    private User user;
}
