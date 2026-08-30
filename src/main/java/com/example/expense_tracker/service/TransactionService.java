package com.example.expense_tracker.service;

import com.example.expense_tracker.dto.ReportResponse;
import com.example.expense_tracker.dto.TransactionFilter;
import com.example.expense_tracker.dto.TransactionRequest;
import com.example.expense_tracker.entity.Category;
import com.example.expense_tracker.entity.Transaction;
import com.example.expense_tracker.enums.TransactionType;
import com.example.expense_tracker.exception.ResourceNotFoundException;
import com.example.expense_tracker.repository.CategoryRepository;
import com.example.expense_tracker.repository.TransactionRepository;
import com.example.expense_tracker.specification.TransactionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final CurrentUser currentUser;

    public Transaction create(TransactionRequest req) {
        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Transaction transaction = Transaction.builder()
                .name(req.getName())
                .amount(req.getAmount())
                .type(req.getType())
                .user(currentUser.getCurrentUser())
                .category(category)
                .build();

        return transactionRepository.save(transaction);
    }

    public Page<Transaction> getAll(
        TransactionFilter filter,
        Pageable pageable
    ) {
        Specification<Transaction> spec = Specification.where(TransactionSpecification.hasType(filter.getType()))
                .and(TransactionSpecification.hasUser(currentUser.getCurrentUser()));
        return transactionRepository.findAll(spec, pageable);
    }

    public Transaction update(String id, TransactionRequest req) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        transaction.setName(req.getName());
        transaction.setAmount(req.getAmount());
        transaction.setType(req.getType());
        transaction.setCategory(category);
        return transactionRepository.save(transaction);
    }

    public void delete(String id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        transactionRepository.delete(transaction);
    }

    public ReportResponse getReport() {
        Integer expense = transactionRepository.getAmountByUserAndType(currentUser.getCurrentUser(), TransactionType.EXPENSE);
        Integer income = transactionRepository.getAmountByUserAndType(currentUser.getCurrentUser(), TransactionType.INCOME);
        if (expense == null) expense = 0;
        if (income == null) income = 0;
        int balance = income - expense;
        return new ReportResponse(balance, income, expense);
    }
}
