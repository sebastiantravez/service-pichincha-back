package com.service.pichincha.service.impl;

import com.service.pichincha.dto.AccountDTO;
import com.service.pichincha.dto.AccountReportDTO;
import com.service.pichincha.dto.AccountReportDetailDTO;
import com.service.pichincha.dto.ClientDTO;
import com.service.pichincha.entities.Account;
import com.service.pichincha.entities.Client;
import com.service.pichincha.entities.enums.AccountType;
import com.service.pichincha.exception.GenericException;
import com.service.pichincha.repository.AccountRepository;
import com.service.pichincha.repository.ClientRepository;
import com.service.pichincha.service.AccountService;
import net.sf.jasperreports.engine.JRException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void saveAccount(AccountDTO accountDTO) {
        Client client = clientRepository.findById(accountDTO.getClient().getClientId())
                .orElseThrow(() -> new GenericException("Cliente no existe, no se puede crear cuenta, debe crear el cliente"));

        Optional<Account> accountQuery = accountRepository.findByAccountTypeAndClient(accountDTO.getAccountType(),
                client);

        if (accountQuery.isPresent()) {
            throw new GenericException("Error: Cliente ya tiene un registro de cuentas con el banco");
        }

        Optional<Account> accountQuery2 = accountRepository.findByAccountNumber(accountDTO.getAccountNumber());
        if (accountQuery2.isPresent()) {
            throw new GenericException("Error: Numero de cuenta ya existe");
        }

        Account account = modelMapper.map(accountDTO, Account.class);
        account.setClient(client);
        account.setStatus(accountDTO.getStatus());
        account.setCreateDate(new Date());
        accountRepository.save(account);
    }

    @Override
    public AccountDTO updateAccount(AccountDTO accountDTO) {
        Account account = accountRepository.findById(accountDTO.getAccountId())
                .orElseThrow(() -> new GenericException("Cuenta de cliente no existe, no se puede actualizar"));
        BigDecimal initialAmount = account.getInitialAmount().setScale(2, RoundingMode.HALF_UP);
        BigDecimal modifyInitialAmount = accountDTO.getInitialAmount().setScale(2, RoundingMode.HALF_UP);
        if (!initialAmount.equals(modifyInitialAmount) && account.getMovements().size() > 0) {
            throw new GenericException("Error: No se puede modificar el saldo inicial, el cliente tiene movimientos");
        }
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setStatus(accountDTO.getStatus());
        account.setAccountType(accountDTO.getAccountType());
        account.setCreateDate(new Date());
        account.setInitialAmount(accountDTO.getInitialAmount());
        accountRepository.save(account);
        return accountDTO;
    }

    @Override
    public void deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new GenericException("Cuenta de cliente no existe, no se puede eliminar"));
        account.setStatus(Boolean.FALSE);
        accountRepository.save(account);
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAllAccounts().stream()
                .filter(account -> account.getStatus())
                .map(this::buildAccountDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> getAccountsByClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new GenericException("Error: Cliente no existe"));
        return accountRepository.findByClient(client)
                .stream().filter(account -> account.getStatus())
                .map(this::buildAccountDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccountReportDTO getAccountStatus(Long clientId, AccountType accountType, Date initDate, Date endDate) {
        Account account = accountRepository.findAccountsMovements(clientId, accountType, initDate, endDate)
                .orElseThrow(() -> new GenericException("Cliente sin movimientos en su cuenta"));
        AccountReportDTO reportDTO = new AccountReportDTO();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String initDateFormat = dateFormat.format(initDate);
        String endDateFormat = dateFormat.format(endDate);
        reportDTO.setFullName(account.getClient().getFullName());
        reportDTO.setDni(account.getClient().getDni());
        reportDTO.setAccountType(account.getAccountType().name());
        reportDTO.setAccountNumber(account.getAccountNumber());
        reportDTO.setInitialAmount(account.getInitialAmount().setScale(2, RoundingMode.HALF_UP));
        reportDTO.setInitDate(initDateFormat);
        reportDTO.setEndDate(endDateFormat);
        reportDTO.setStatus(account.getStatus() ? "ACTIVO" : "INACTIVO");
        List<AccountReportDetailDTO> detail = account.getMovements().stream()
                .map(item -> {
                    AccountReportDetailDTO line = new AccountReportDetailDTO();
                    line.setTransactionType(item.getTransactionType().name());
                    line.setMovementDate(dateFormat.format(item.getMovementDate()));
                    line.setMovementType(item.getMovementType().name());
                    line.setMovementAmount(item.getMovementAmount());
                    line.setBalanceAvailable(item.getBalanceAvailable().setScale(2, RoundingMode.HALF_UP));
                    line.setObservation(item.getObservation());
                    return line;
                }).collect(Collectors.toList());
        reportDTO.setAccountReportDetail(detail);
        return reportDTO;
    }

    private AccountDTO buildAccountDTO(Account account) {
        ClientDTO clientDTO = modelMapper.map(account.getClient(), ClientDTO.class);
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
        accountDTO.setClient(clientDTO);
        return accountDTO;
    }

}
