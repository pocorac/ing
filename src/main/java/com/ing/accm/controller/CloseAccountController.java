package com.ing.accm.controller;

import com.ing.accm.model.Account;
import com.ing.accm.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CloseAccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/closeAccount/{id}")
    public Account closeAccount(@PathVariable("id") Long accountId) {
        log.info("Start close account ID {}", accountId);
        return accountService.closeAccount(accountId);
    }

}
