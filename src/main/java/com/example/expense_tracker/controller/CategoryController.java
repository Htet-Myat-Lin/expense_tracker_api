package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.ApiResponse;
import com.example.expense_tracker.entity.Category;
import com.example.expense_tracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(ApiResponse.success(categories));
    }
}
