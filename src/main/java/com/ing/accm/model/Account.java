package com.ing.accm.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Entity
@Where(clause = "active = true")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String accountName;
    private String accountNumber;
    private BigDecimal currentBalance;
    private Boolean active;
    private ZonedDateTime openDate;
    private ZonedDateTime closeDate;

    public Account() {
    }

    public Account(String accountName) {
        this.accountName = accountName;
        this.accountNumber = generateAccountNumber();
        this.currentBalance = BigDecimal.ZERO;
        this.active = Boolean.TRUE;
        this.openDate = ZonedDateTime.now(ZoneOffset.UTC);
    }

    public static String generateAccountNumber() {
        return UUID.randomUUID().toString();
    }

    public void deposit(BigDecimal amount) {
        currentBalance = currentBalance.add(amount);
    }

    public Boolean withdraw(BigDecimal amount) {
        if (currentBalance.compareTo(amount) >= 0) {
            currentBalance = currentBalance.subtract(amount);
            return true;
        }
        return false;
    }

    public void close() {
        this.levelBalance();
        this.active = Boolean.FALSE;
        this.closeDate = ZonedDateTime.now(ZoneOffset.UTC);
    }

    private void levelBalance() {
        int level = currentBalance.compareTo(BigDecimal.ZERO);

        if (level > 0) {
            withdraw(currentBalance);
        } else if (level < 0) {
            deposit(BigDecimal.ZERO.subtract(currentBalance));
        }
    }
}
