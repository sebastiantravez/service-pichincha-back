package com.service.pichincha.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountReportDetailDTO {
    private String transactionType;
    private String movementDate;
    private String movementType;
    private BigDecimal movementAmount;
    private BigDecimal balanceAvailable;
    private String observation;
}
