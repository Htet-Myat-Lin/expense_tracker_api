package com.example.expense_tracker.dto;

import com.example.expense_tracker.enums.TransactionType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private String id;
    private String name;
    private Double amount;
    private TransactionType type;
    private CategoryResponse category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
