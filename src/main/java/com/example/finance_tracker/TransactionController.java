package com.example.finance_tracker;

import jakarta.transaction.InvalidTransactionException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TransactionController {
    private  TransactionService transactionService;
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @GetMapping("/api/transactions")
    public BigDecimal getBalance() {
return transactionService.calculateBalance();
    }

    @GetMapping("/api/AllTransaction")
    public List<Transaction> getAllTransactions() {
        return transactionService.getTransaction();
    }
    @GetMapping("/api/transactions/between")
    public List<Transaction> getTransactionsBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        return transactionService.getTransactionDateBetween(startDate, endDate);
    }

    @GetMapping("/api/dateBetween")
    public List<Transaction> getByTransactionDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        // Removed the extra { that was here
        return transactionService.getTransactionsBetween(startDate, endDate);
    }

    @GetMapping("/api/getID")
    public List<Transaction> getId(@RequestParam List<Long> ids) { // Added @RequestParam
        return transactionService.getByID(ids);
    }

    @DeleteMapping("/api/delete")
    public List<Transaction> deleteByID(@RequestParam List<Long> ids) {
        return transactionService.deleteByID(ids);
    }

    @PostMapping("/add")
    public Transaction addTransaction(@RequestBody Transaction transaction) throws InvalidTransactionException {
        return transactionService.saveTransaction(transaction);
    }
}
