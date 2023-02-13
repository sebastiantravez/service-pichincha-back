package com.service.pichincha.util;

import com.service.pichincha.dto.AccountDTO;
import com.service.pichincha.entities.Account;
import com.service.pichincha.entities.Client;
import com.service.pichincha.entities.enums.AccountType;
import com.service.pichincha.entities.enums.GenderPerson;

import java.util.Date;

public class TestData {

    public Client client() {
        Client client = new Client();
        client.setId(100L);
        client.setFullName("PRUEBAS PRUEBAS");
        client.setAge(12);
        client.setGenderPerson(GenderPerson.MASCULINO);
        client.setAddress("PUEBAS");
        return client;
    }

    public Account account() {
        Account account = new Account();
        account.setAccountId(1000L);
        account.setAccountNumber("23434");
        account.setAccountType(AccountType.AHORROS);
        account.setCreateDate(new Date());
        account.setClient(client());
        account.setStatus(true);
        return account;
    }

    public AccountDTO accountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountId(account.getAccountId());
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setAccountType(account.getAccountType());
        accountDTO.setStatus(account.getStatus());
        return accountDTO;
    }
}

