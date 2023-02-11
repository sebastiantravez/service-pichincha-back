package com.service.pichincha.entities;

import com.service.pichincha.entities.enums.AccountType;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue
    private UUID accountId;
    @NotNull
    @Column(unique = true)
    private String accountNumber;
    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @PositiveOrZero
    private BigDecimal initialAmount;
    @Builder.Default
    private Boolean status = Boolean.TRUE;
    private Date createDate;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<Movements> movements = new ArrayList<>();
}
