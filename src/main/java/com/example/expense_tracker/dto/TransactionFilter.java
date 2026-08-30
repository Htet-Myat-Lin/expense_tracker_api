package com.example.expense_tracker.dto;

import com.example.expense_tracker.enums.TransactionType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionFilter {
    private TransactionType type;
    private Integer page;
    private Integer limit;
}
