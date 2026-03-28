package com.example.finance_tracker;

import jakarta.transaction.InvalidTransactionException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public List<Transaction> getTransactionDateBetween(LocalDateTime  startDate, LocalDateTime endDate) {
        List<Transaction> list = new ArrayList<Transaction>();
        for(Transaction t : repository.findAll()) {
            if(t.getCreatedAt().isAfter(startDate) && t.getCreatedAt().isBefore(endDate)) {
                list.add(t);
            }

        }
        return  list;

    }

    public List<Transaction> getTransactionsBetween(LocalDateTime start, LocalDateTime end) {
        return repository.findByTransactionDateBetween(start, end);
    }
    public List<Transaction> getByID(List<Long> ids) {
        return repository.findByIdIn(ids);
    }


    @Transactional
    public List<Transaction> deleteByID(List<Long> ids) {
        // 1. Find them first so we can return them to the user
        List<Transaction> toDelete = repository.findByIdIn(ids);

        // 2. Delete them from the database
        repository.deleteByIdIn(ids);

        // 3. Return the list we found in step 1
        return toDelete;
    }




    }

