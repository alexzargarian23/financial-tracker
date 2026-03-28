package com.example.finance_tracker;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")  // Standard naming: lowercase, plural
public class Transaction {

    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)  // Optional: limit description length
    private String description;

    @Column(nullable = false)
    private BigDecimal amount;  // Use Double for monetary values

    @Column(nullable = false)
    private LocalDateTime transactionDate;  // More modern than Date

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private transactionTypeEnum transactionType;



    public Transaction(BigDecimal amount, String description,
                       LocalDateTime transactionDate, transactionTypeEnum transactionType) {
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}