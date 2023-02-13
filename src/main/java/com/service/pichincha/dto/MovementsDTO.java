package com.service.pichincha.dto;


import com.service.pichincha.entities.enums.MovementType;
import com.service.pichincha.entities.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementsDTO {
    private Long movementId;
    private Date movementDate;
    private MovementType movementType;
    private BigDecimal movementAmount;
    private BigDecimal balanceAvailable;
    private TransactionType transactionType;
    private AccountDTO account;
    private String observation;
}
