package com.example.expense_tracker.dto;

import com.example.expense_tracker.enums.TransactionType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    private String id;
    private String name;
    private TransactionType type;
}
