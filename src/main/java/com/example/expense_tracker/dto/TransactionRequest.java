package com.example.expense_tracker.dto;

import com.example.expense_tracker.enums.TransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be greater than 0")
    private Double amount;

    @NotNull(message = "Type is required")
    private TransactionType type;

    @NotNull(message = "Category is required")
    private String categoryId;
}
