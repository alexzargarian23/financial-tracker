package com.example.finance_tracker;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class TransactionController {
    private  TransactionService transactionService;
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @RequestMapping("/api/transactions")
    public BigDecimal getBalance() {
return transactionService.calculateBalance();
    }

    @RequestMapping("/api/AllTransaction")
    public List<Transaction> getAllTransactions() {
        return transactionService.getTransaction();
    }


}
