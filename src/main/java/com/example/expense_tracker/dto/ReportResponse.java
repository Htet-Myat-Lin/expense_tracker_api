package com.example.expense_tracker.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReportResponse {
    private int balance;
    private int income;
    private int expense;
}
