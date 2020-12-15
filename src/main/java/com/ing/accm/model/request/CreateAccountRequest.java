package com.ing.accm.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountRequest {

    private String accountName;
    private BigDecimal amount;
}
