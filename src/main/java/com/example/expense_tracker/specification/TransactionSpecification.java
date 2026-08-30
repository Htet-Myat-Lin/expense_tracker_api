package com.example.expense_tracker.specification;

import com.example.expense_tracker.entity.Transaction;
import com.example.expense_tracker.entity.User;
import com.example.expense_tracker.enums.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class TransactionSpecification {
    public static Specification<Transaction> hasType (TransactionType type) {
        return (root, query, cb) -> type == null ? null : cb.equal(root.get("type"), type);
    }

    public static Specification<Transaction> hasUser (User user) {
        return (root, query, cb) -> user == null ? null : cb.equal(root.get("user"), user);
    }
}
