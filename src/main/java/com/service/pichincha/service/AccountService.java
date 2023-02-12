package com.service.pichincha.service;

import com.service.pichincha.dto.AccountDTO;
import com.service.pichincha.dto.AccountReportDTO;
import com.service.pichincha.entities.enums.AccountType;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface AccountService {
    void saveAccount(AccountDTO accountDTO);

    AccountDTO updateAccount(AccountDTO accountDTO);

    void deleteAccount(Long accountId);

    List<AccountDTO> getAllAccounts();

    List<AccountDTO> getAccountsByClient(Long clientId);

    AccountReportDTO getAccountStatus(Long clientId, AccountType accountType, Date initDate, Date endDate) throws JRException, IOException;
}
