package com.ing.accm.service;

import com.ing.accm.model.Account;
import com.ing.accm.model.Transaction;
import com.ing.accm.model.TransactionType;
import com.ing.accm.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public void createTransaction(TransactionType transactionType, Account account, BigDecimal amount) {
        log.info("Start create transaction {} amount {} for account {}", transactionType.name(), amount, account.getAccountName());
        Transaction transaction = new Transaction(transactionType, account, amount);
        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsForAccount(Long accountId, ZonedDateTime beginWith) {
        log.info("Find transactions for account ID {} starting with {}", accountId, beginWith);
        return transactionRepository.findByAccountIdAndTransactionDateAfter(accountId, beginWith);
    }

    public void addTransaction(Transaction transaction, Account account) {
        log.info("Start add transaction {} amount {} for account {}", transaction.getType().name(), transaction.getAmount(), account.getAccountName());
        transaction.setAccount(account);
        if (transaction.addTransaction()) {
            transactionRepository.save(transaction);
        }
    }
}
