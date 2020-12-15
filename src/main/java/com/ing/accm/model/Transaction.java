package com.ing.accm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;

    private BigDecimal amount;

    private ZonedDateTime transactionDate;

    public Transaction() {
    }

    public Transaction(TransactionType type, Account account, BigDecimal amount) {
        this.type = type;
        this.account = account;
        this.amount = amount;
        this.transactionDate = ZonedDateTime.now(ZoneOffset.UTC);
    }

    public Boolean addTransaction() {
        if (TransactionType.DEPOSIT.equals(this.type)) {
            this.account.deposit(this.amount);
            return true;
        } else if (TransactionType.WITHDRAW.equals(this.type)) {
            return this.account.withdraw(this.amount);
        }
        return false;
    }
}
