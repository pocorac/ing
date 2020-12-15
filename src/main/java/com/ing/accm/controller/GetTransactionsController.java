package com.ing.accm.controller;

import com.ing.accm.model.Transaction;
import com.ing.accm.model.request.TransactionsRequest;
import com.ing.accm.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GetTransactionsController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions")
    public List<Transaction> findTransactions(@RequestBody TransactionsRequest transactionsRequest) {
        return transactionService.getTransactionsForAccount(transactionsRequest.getAccountId(), transactionsRequest.getBeginWith());
    }
}
