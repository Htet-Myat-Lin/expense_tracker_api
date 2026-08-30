package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.ApiResponse;
import com.example.expense_tracker.dto.ReportResponse;
import com.example.expense_tracker.dto.TransactionFilter;
import com.example.expense_tracker.dto.TransactionRequest;
import com.example.expense_tracker.entity.Transaction;
import com.example.expense_tracker.enums.TransactionType;
import com.example.expense_tracker.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ApiResponse<Transaction>> createTransaction (
        @Valid @RequestBody TransactionRequest request
    ) {
        Transaction newTransaction = transactionService.create(request);
        return ResponseEntity.ok(ApiResponse.success(newTransaction));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Transaction>>> getTransactions (
        @RequestParam(required = false) TransactionType type,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer limit
    ) {
        TransactionFilter filter = TransactionFilter.builder()
                .type(type)
                .page(page)
                .limit(limit)
                .build();

        int pageNumber = page == null ? 0 : page.intValue();
        int pageSize = limit == null ? 10 : limit.intValue();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Transaction> transactions = transactionService.getAll(filter, pageable);
        return ResponseEntity.ok(ApiResponse.success(transactions));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Transaction>> updateTransaction (
        @Valid @RequestBody TransactionRequest request,
        @PathVariable String id
    ) {
        Transaction updatedTransaction = transactionService.update (id, request);
        return ResponseEntity.ok(ApiResponse.success(updatedTransaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTransaction (@PathVariable String id) {
        transactionService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Transaction deleted successfully"));
    }

    @GetMapping("/report")
    public ResponseEntity<ApiResponse<ReportResponse>> getReport() {
        ReportResponse report = transactionService.getReport();
        return ResponseEntity.ok(ApiResponse.success(report));
    }
}
