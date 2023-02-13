package com.service.pichincha.entities;

import com.service.pichincha.entities.enums.AccountType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue
    private Long accountId;
    @NotNull
    private String accountNumber;
    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @PositiveOrZero
    private BigDecimal initialAmount;
    private Boolean status;
    @NotNull
    private Date createDate;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @NotNull
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.EAGER)
    private List<Movements> movements;
}
