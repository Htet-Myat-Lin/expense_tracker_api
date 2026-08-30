package com.example.expense_tracker.repository;

import com.example.expense_tracker.entity.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, String> {
    boolean existsByToken(String token);
}
