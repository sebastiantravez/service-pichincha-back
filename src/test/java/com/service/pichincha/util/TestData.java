package com.service.pichincha.util;

import com.service.pichincha.dto.AccountDTO;
import com.service.pichincha.dto.ClientDTO;
import com.service.pichincha.dto.MovementsDTO;
import com.service.pichincha.entities.Account;
import com.service.pichincha.entities.Client;
import com.service.pichincha.entities.Movements;
import com.service.pichincha.entities.enums.AccountType;
import com.service.pichincha.entities.enums.GenderPerson;
import com.service.pichincha.entities.enums.MovementType;
import com.service.pichincha.entities.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

public class TestData {

    public Client client() {
        Client client = new Client();
        client.setClientId(100L);
        client.setFullName("PRUEBAS PRUEBAS");
        client.setAge(12);
        client.setGenderPerson(GenderPerson.MASCULINO);
        client.setAddress("PUEBAS");
        return client;
    }

    public Account account() {
        return Account.builder()
                .accountId(1000L)
                .accountNumber("48632487")
                .accountType(AccountType.AHORROS)
                .createDate(new Date())
                .initialAmount(BigDecimal.TEN)
                .client(client())
                .status(true)
                .build();
    }

    public AccountDTO accountDTO(Account account) {
        return AccountDTO.builder()
                .accountId(account.getAccountId())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .initialAmount(account.getInitialAmount())
                .status(account.getStatus())
                .client(ClientDTO.builder()
                        .clientId(100L)
                        .fullName("PRUEBAS PRUEBAS")
                        .dni("12386127836218")
                        .age(12)
                        .build())
                .build();
    }

    public Movements movements() {
        return Movements.builder()
                .account(account())
                .movementId(1000L)
                .movementAmount(BigDecimal.valueOf(4))
                .movementType(MovementType.CREDITO)
                .movementDate(new Date())
                .balanceAvailable(BigDecimal.TEN)
                .transactionType(TransactionType.APROBADA)
                .build();
    }

    public MovementsDTO movementsDTO(Movements movements) {
        return MovementsDTO.builder()
                .account(accountDTO(account()))
                .movementId(movements.getMovementId())
                .movementAmount(movements.getMovementAmount())
                .movementType(movements.getMovementType())
                .movementDate(new Date())
                .balanceAvailable(movements.getBalanceAvailable())
                .transactionType(movements.getTransactionType())
                .build();
    }
}

