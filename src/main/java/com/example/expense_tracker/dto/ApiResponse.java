package com.example.expense_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T content;

    public static <T> ApiResponse<T> success(T content) {
        return new ApiResponse<>(true, "Success", content);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
}
