package com.service.pichincha.controller;

import com.service.pichincha.dto.AccountDTO;
import com.service.pichincha.dto.AccountReportDTO;
import com.service.pichincha.entities.enums.AccountType;
import com.service.pichincha.service.AccountService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/saveAccount")
    public void saveAccount(@RequestBody AccountDTO accountDTO) {
        accountService.saveAccount(accountDTO);
    }

    @PutMapping("/updateAccount")
    public void updateAccount(@RequestBody AccountDTO accountDTO) {
        accountService.updateAccount(accountDTO);
    }

    @GetMapping("/getAllAccounts")
    public List<AccountDTO> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @DeleteMapping("/deleteAccount")
    public void deleteAccount(@RequestParam Long accountId) {
        accountService.deleteAccount(accountId);
    }

    @GetMapping("/getAccountsByClient")
    public List<AccountDTO> getAccountsByClient(@RequestParam Long clientId) {
        return accountService.getAccountsByClient(clientId);
    }

    @GetMapping("/getAccountStatusReport")
    public AccountReportDTO getReportAccount(@RequestParam Long clientId, AccountType accountType,
                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date initDate,
                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) throws JRException, IOException {
        return accountService.getAccountStatus(clientId, accountType, initDate, endDate);
    }

}
