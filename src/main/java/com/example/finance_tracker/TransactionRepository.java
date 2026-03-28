package com.example.finance_tracker;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByTransactionType(transactionTypeEnum transactionType);
    List<Transaction> findByAmountGreaterThan(BigDecimal amount);
    List<Transaction> findByAmountLessThan(BigDecimal amount);
    List<Transaction> findByAmountBetween(BigDecimal from, BigDecimal to);
    List<Transaction> findByDescriptionContaining(String description);
    List<Transaction> findByIdIn(List<Long> ids);


}
