package com.ing.accm.controller;

import com.ing.accm.model.Account;
import com.ing.accm.model.request.CreateAccountRequest;
import com.ing.accm.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CreateAccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/createAccount")
    public Account createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        log.info("Start create account " + createAccountRequest.getAccountName());
        return accountService.createAccount(createAccountRequest);
    }
}
