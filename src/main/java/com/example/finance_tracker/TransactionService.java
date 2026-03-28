package com.example.finance_tracker;

import jakarta.transaction.InvalidTransactionException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository repository;


    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction createTransaction(Transaction transaction) {
        return repository.save(transaction);
    }
    public Transaction saveTransaction(Transaction transaction) throws InvalidTransactionException {
        if (transaction.getAmount().compareTo(BigDecimal.ZERO)<=0 ) {
throw new InvalidTransactionException("Amount must be positive");
        }
return repository.save(transaction);
    }

    public BigDecimal calculateBalance() {
        List<Transaction> list = repository.findAll();

        if (list.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal balance = BigDecimal.ZERO;
        for (Transaction t : list) {
            // Ensure we handle potential nulls for safety
            if (t.getAmount() == null || t.getTransactionType() == null) continue;

            if (t.getTransactionType().equals(transactionTypeEnum.EXPENSE)) {
                balance = balance.subtract(t.getAmount());
            } else {
                balance = balance.add(t.getAmount());
            }
        }
        return balance;
    }


    public List<Transaction> getTransaction() {
        return repository.findAll();
    }





    }

