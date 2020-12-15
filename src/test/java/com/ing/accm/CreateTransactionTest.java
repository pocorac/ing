package com.ing.accm;

import com.ing.accm.model.Account;
import com.ing.accm.model.Transaction;
import com.ing.accm.model.TransactionType;
import com.ing.accm.repository.TransactionRepository;
import com.ing.accm.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateTransactionTest {

    private static final BigDecimal AMOUNT = BigDecimal.TEN;

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private Account account;

    @Mock
    private Transaction transaction;

    @Test
    public void createTransactionTest() {
        transactionService.createTransaction(TransactionType.DEPOSIT, account, AMOUNT);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    public void addTransactionTest(){
        when(transaction.getType()).thenReturn(TransactionType.WITHDRAW);
        when(transaction.addTransaction()).thenReturn(Boolean.TRUE);
        transactionService.addTransaction(transaction, account);
        verify(transactionRepository).save(any(Transaction.class));
    }
}
