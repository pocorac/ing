package com.ing.accm;

import com.ing.accm.model.Account;
import com.ing.accm.model.TransactionType;
import com.ing.accm.model.request.CreateAccountRequest;
import com.ing.accm.repository.AccountRepository;
import com.ing.accm.service.AccountService;
import com.ing.accm.service.TransactionService;
import com.ing.accm.service.TransactionsReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CreateAccountTest {

    private static final String ACCOUNT_NAME = "name";
    private static final BigDecimal AMOUNT = BigDecimal.TEN;

    @InjectMocks
    private AccountService accountService;

    @Mock
    private CreateAccountRequest createAccountRequest;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionsReader transactionsReader;

    @Mock
    private TransactionService transactionService;

    @Mock
    private Account account;

    @Before
    public void setup() {
        when(createAccountRequest.getAccountName()).thenReturn(ACCOUNT_NAME);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
    }

    @Test
    public void createAccountWithInitialTransaction() {
        when(createAccountRequest.getAmount()).thenReturn(AMOUNT);
        accountService.createAccount(createAccountRequest);

        verify(accountRepository).save(any(Account.class));
        verify(transactionService).createTransaction(eq(TransactionType.DEPOSIT), any(Account.class), eq(AMOUNT));
    }

    @Test
    public void createAccountWithNoInitialTransaction() {
        when(createAccountRequest.getAmount()).thenReturn(BigDecimal.ZERO);
        accountService.createAccount(createAccountRequest);

        verify(accountRepository).save(any(Account.class));
        verify(transactionService, never()).createTransaction(any(TransactionType.class), any(Account.class), any());
    }


}
