package com.service.pichincha.entities;

import com.service.pichincha.entities.enums.MovementType;
import com.service.pichincha.entities.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "movements")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movements {
    @Id
    private Long movementId;
    private Date movementDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    private MovementType movementType;
    private String observation;
    private BigDecimal movementAmount;
    private BigDecimal balanceAvailable;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
