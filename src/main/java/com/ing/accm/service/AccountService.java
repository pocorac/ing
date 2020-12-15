package com.ing.accm.service;

import com.ing.accm.exception.EntityNotFoundException;
import com.ing.accm.model.Account;
import com.ing.accm.model.Transaction;
import com.ing.accm.model.TransactionType;
import com.ing.accm.model.request.CreateAccountRequest;
import com.ing.accm.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionsReader transactionsReader;

    public Account createAccount(CreateAccountRequest createAccountRequest) {
        Account account = new Account(createAccountRequest.getAccountName());
        accountRepository.save(account);
        if (createAccountRequest.getAmount() != null && createAccountRequest.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            deposit(account, createAccountRequest.getAmount());
        }

        log.info("Add transactions from static JSON file for account ID {}.", account.getId());
        List<Transaction> transactions = transactionsReader.readTransactionsJson();
        transactions.forEach(t -> transactionService.addTransaction(t, account));

        return account;
    }

    public Account closeAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new EntityNotFoundException("Account ID not found or previously closed: " + accountId));
        account.close();
        accountRepository.save(account);
        log.info("Account ID {} was closed.", accountId);
        return account;
    }

    private void deposit(Account account, BigDecimal amount) {
        account.deposit(amount);
        transactionService.createTransaction(TransactionType.DEPOSIT, account, amount);
        log.info("Deposit amount {} for account {} ended.", amount, account.getAccountName());
    }

    private void withdraw(Account account, BigDecimal amount) {
        if (account.withdraw(amount)) {
            transactionService.createTransaction(TransactionType.WITHDRAW, account, amount);
            log.info("Withdraw amount {} for account {} ended.", amount, account.getAccountName());
            return;
        }
        log.info("Withdraw amount {} for account {} failed, insufficient funds.", amount, account.getAccountName());
    }

}
