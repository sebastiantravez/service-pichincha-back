package com.service.pichincha.service.impl;

import com.service.pichincha.dto.AccountDTO;
import com.service.pichincha.dto.ClientDTO;
import com.service.pichincha.dto.MovementsDTO;
import com.service.pichincha.entities.Account;
import com.service.pichincha.entities.Movements;
import com.service.pichincha.entities.enums.AmountLimit;
import com.service.pichincha.entities.enums.MovementType;
import com.service.pichincha.entities.enums.TransactionType;
import com.service.pichincha.exception.GenericException;
import com.service.pichincha.repository.AccountRepository;
import com.service.pichincha.repository.MovementsRepository;
import com.service.pichincha.service.MovementsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovementsServiceImpl implements MovementsService {

    @Autowired
    private MovementsRepository movementsRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void saveMovement(MovementsDTO movementsDTO) {
        if (movementsDTO.getMovementAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "Error: El valor del movimiento debe ser superior a 0");
        }
        Account account = accountRepository.findById(movementsDTO.getAccount().getAccountId())
                .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Error: Cuenta de cliente no existe"));

        //TODO here init first movement
        Movements movements = new Movements();
        if (account.getMovements().isEmpty()) {
            switch (movementsDTO.getMovementType()) {
                case DEBITO:
                    if (movementsDTO.getMovementAmount().compareTo(account.getInitialAmount()) > 0) {
                        throw new GenericException(HttpStatus.BAD_REQUEST, "Error: El valor del retiro debe ser menor o igual al saldo disponible del cliente");
                    }
                    BigDecimal amountAvailableDebit = account.getInitialAmount()
                            .subtract(movementsDTO.getMovementAmount()).setScale(2, RoundingMode.HALF_UP);
                    if (amountAvailableDebit.compareTo(BigDecimal.ZERO) < 0) {
                        throw new GenericException(HttpStatus.BAD_REQUEST, "Error: Saldo no disponible");
                    }
                    movements.setMovementAmount(movementsDTO.getMovementAmount());
                    movements.setBalanceAvailable(amountAvailableDebit);
                    break;
                case CREDITO:
                    BigDecimal amountAvailableCredit = account.getInitialAmount()
                            .add(movementsDTO.getMovementAmount()).setScale(2, RoundingMode.HALF_UP);
                    movements.setMovementAmount(movementsDTO.getMovementAmount());
                    movements.setBalanceAvailable(amountAvailableCredit);
                    break;
            }
        } else {
            Movements movementsQuery = movementsRepository.findLastMove(movementsDTO.getAccount().getAccountId(), TransactionType.APROBADA.name())
                    .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Error: Cuenta sin movimientos"));
            switch (movementsDTO.getMovementType()) {
                case DEBITO:
                    if (movementsQuery.getBalanceAvailable().compareTo(BigDecimal.ZERO) <= 0) {
                        throw new GenericException(HttpStatus.BAD_REQUEST, "Error: Saldo no disponible");
                    }
                    if (movementsDTO.getMovementAmount().compareTo(movementsQuery.getBalanceAvailable()) > 0) {
                        throw new GenericException(HttpStatus.BAD_REQUEST, "Error: El valor del retiro debe ser menor o igual al saldo disponible del cliente, " +
                                "Su saldo disponible es de $:" + movementsQuery.getBalanceAvailable().setScale(2, RoundingMode.HALF_UP));
                    }

                    //TODO verify limit amount per day
                    this.verifyLimitAmount(account, movementsDTO);

                    BigDecimal amountAvailableDebit = movementsQuery.getBalanceAvailable()
                            .subtract(movementsDTO.getMovementAmount()).setScale(2, RoundingMode.HALF_UP);
                    movements.setMovementAmount(movementsDTO.getMovementAmount());
                    movements.setBalanceAvailable(amountAvailableDebit);
                    break;
                case CREDITO:
                    BigDecimal amountAvailableCredit = movementsQuery.getBalanceAvailable()
                            .add(movementsDTO.getMovementAmount()).setScale(2, RoundingMode.HALF_UP);
                    movements.setMovementAmount(movementsDTO.getMovementAmount());
                    movements.setBalanceAvailable(amountAvailableCredit);
                    break;
            }
        }
        movements.setAccount(account);
        movements.setMovementType(movementsDTO.getMovementType());
        movements.setMovementDate(new Date());
        movements.setObservation(movementsDTO.getObservation());
        movements.setTransactionType(TransactionType.APROBADA);
        movementsRepository.save(movements);
    }

    private void verifyLimitAmount(Account account, MovementsDTO movementsDTO){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date lastMovementDate = account.getMovements().stream()
                .filter(movementType -> movementType.getMovementType().equals(MovementType.DEBITO))
                .map(data -> data.getMovementDate())
                .max(Date::compareTo).get();

        String nowDate = simpleDateFormat.format(new Date());
        String lastMovementDateString = simpleDateFormat.format(lastMovementDate);

        BigDecimal amountAccumulated = account.getMovements().stream()
                .filter(movementsFilter -> movementsFilter.getMovementType().equals(MovementType.DEBITO))
                .filter(dates -> {
                    String dateFilter = simpleDateFormat.format(dates.getMovementDate());
                    return lastMovementDateString.equals(dateFilter);
                })
                .map(amount -> amount.getMovementAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (amountAccumulated.add(movementsDTO.getMovementAmount())
                .compareTo(AmountLimit.LIMIT_DAY.getValue()) > 0 && nowDate.equals(lastMovementDateString)) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "Error: Cupo diario excedido");
        }
    }

    @Override
    public MovementsDTO updateMovement(MovementsDTO movementsDTO) {
        Movements movement = movementsRepository.findById(movementsDTO.getMovementId())
                .orElseThrow(() -> new GenericException(HttpStatus.BAD_REQUEST, "Error: Movimiento no existe"));
        switch (movementsDTO.getMovementType()) {
            case DEBITO:
                if (movementsDTO.getMovementAmount().compareTo(movement.getBalanceAvailable()) > 0) {
                    throw new GenericException(HttpStatus.BAD_REQUEST, "Error: El valor del retiro debe ser menor o igual al saldo disponible del cliente, " +
                            "Su saldo disponible es de $:" + movement.getBalanceAvailable().setScale(2, RoundingMode.HALF_UP));
                }
                BigDecimal amountAvailableDebit = movement.getBalanceAvailable().add(movement.getMovementAmount())
                        .subtract(movementsDTO.getMovementAmount()).setScale(2, RoundingMode.HALF_UP);
                movement.setMovementAmount(movementsDTO.getMovementAmount());
                movement.setBalanceAvailable(amountAvailableDebit);
                break;
            case CREDITO:
                BigDecimal amountAvailableCredit = movement.getBalanceAvailable()
                        .add(movementsDTO.getMovementAmount()).setScale(2, RoundingMode.HALF_UP);
                movement.setMovementAmount(movementsDTO.getMovementAmount());
                movement.setBalanceAvailable(amountAvailableCredit);
                break;
        }
        movement.setMovementType(movementsDTO.getMovementType());
        movement.setMovementDate(new Date());
        movement.setObservation(movementsDTO.getObservation());
        movementsRepository.save(movement);
        return movementsDTO;
    }

    @Override
    public List<MovementsDTO> getAllMovements() {
        return movementsRepository.finAllMovements().stream()
                .filter(movements -> movements.getTransactionType().equals(TransactionType.APROBADA))
                .map(this::buildMovementDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMovement(Long movementId) {
        Movements movementCanceled = movementsRepository.findById(movementId)
                    .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "No existe movimiento"));
        Movements movementsApproved = movementsRepository.findLastMove(movementCanceled.getAccount().getAccountId(), TransactionType.APROBADA.name())
                .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "Error: Cuenta sin movimientos"));
        Movements newMovement = new Movements();
        switch (movementCanceled.getMovementType()) {
            case DEBITO:
                BigDecimal amountCredit = movementsApproved.getBalanceAvailable()
                        .add(movementCanceled.getMovementAmount()).setScale(2, RoundingMode.HALF_UP);
                newMovement.setMovementAmount(movementCanceled.getMovementAmount());
                newMovement.setBalanceAvailable(amountCredit);
                newMovement.setTransactionType(TransactionType.APROBADA);
                newMovement.setMovementType(MovementType.CREDITO);
                newMovement.setObservation("SE ACREDITA VALOR POR CANCELACION DE TRANSACCION");
                newMovement.setAccount(movementsApproved.getAccount());
                newMovement.setMovementDate(new Date());
                break;
            case CREDITO:
                BigDecimal amountDebit = movementsApproved.getBalanceAvailable()
                        .subtract(movementCanceled.getMovementAmount()).setScale(2, RoundingMode.HALF_UP);
                newMovement.setMovementAmount(movementCanceled.getMovementAmount());
                newMovement.setBalanceAvailable(amountDebit);
                newMovement.setTransactionType(TransactionType.APROBADA);
                newMovement.setMovementType(MovementType.DEBITO);
                newMovement.setObservation("SE DEBITA VALOR POR CANCELACION DE TRANSACCION");
                newMovement.setMovementDate(new Date());
                newMovement.setAccount(movementsApproved.getAccount());
                break;
        }
        movementsRepository.save(newMovement);

        movementCanceled.setTransactionType(TransactionType.CANCELADA);
        movementCanceled.setMovementDate(new Date());
        movementsRepository.save(movementCanceled);
    }

    public MovementsDTO buildMovementDTO(Movements movements) {
        MovementsDTO movementsDTO = modelMapper.map(movements, MovementsDTO.class);
        AccountDTO accountDTO = modelMapper.map(movements.getAccount(), AccountDTO.class);
        ClientDTO clientDTO = modelMapper.map(movements.getAccount().getClient(), ClientDTO.class);
        accountDTO.setClient(clientDTO);
        movementsDTO.setAccount(accountDTO);
        return movementsDTO;
    }
}
