package com.example.expense_tracker.exception;

import com.example.expense_tracker.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFoundException (
        ResourceNotFoundException exception
    ) {
        ApiResponse<String> response = ApiResponse.error(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
        MethodArgumentNotValidException exception
    ) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        exception.getBindingResult().getGlobalErrors().forEach(error ->
            errors.put(error.getObjectName(), error.getDefaultMessage())
        );
        ApiResponse<Map<String, String>> response = new ApiResponse<>(false,"Validation failed", errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception exception) {
        ApiResponse<String> response = ApiResponse.error(exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
