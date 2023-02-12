package com.service.pichincha.dto;

import com.service.pichincha.entities.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {
    private Long accountId;
    @NotNull
    private String accountNumber;
    @NotNull
    private AccountType accountType;
    @NotNull
    private BigDecimal initialAmount;
    @Builder.Default
    private Boolean status = Boolean.TRUE;
    private ClientDTO client;
    private byte[] pdf;
}
