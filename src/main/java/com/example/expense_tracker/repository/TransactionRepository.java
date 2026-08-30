package com.example.expense_tracker.repository;

import com.example.expense_tracker.entity.Transaction;
import com.example.expense_tracker.entity.User;
import com.example.expense_tracker.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    Page<Transaction> findAll (Specification<Transaction> spec, Pageable pageable);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user = :user AND t.type = :type")
    Integer getAmountByUserAndType(User user, TransactionType type);
}
