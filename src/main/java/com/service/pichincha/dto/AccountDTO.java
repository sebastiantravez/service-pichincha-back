package com.service.pichincha.dto;

import com.service.pichincha.entities.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long accountId;
    private String accountNumber;
    private AccountType accountType;
    private BigDecimal initialAmount;
    private Boolean status;
    private ClientDTO client;
}
