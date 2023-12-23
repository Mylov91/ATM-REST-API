package ru.mylov.ATM.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mylov.ATM.exceptions.InsufficientFundsException;
import ru.mylov.ATM.models.Operation;
import ru.mylov.ATM.models.OperationType;
import ru.mylov.ATM.repositories.OperationsRepository;
import ru.mylov.ATM.utils.ResponseMessage;
import ru.mylov.ATM.exceptions.UserNotFoundException;
import ru.mylov.ATM.models.User;
import ru.mylov.ATM.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class ProcessingService {
    private final UserRepository userRepository;
    private final OperationsRepository operationsRepository;

    @Autowired
    public ProcessingService(UserRepository userRepository, OperationsRepository operationsRepository) {
        this.userRepository = userRepository;
        this.operationsRepository = operationsRepository;
    }

    public long getBalance(long id) {
        Optional<User> foundUser = userRepository.findById(id);
        return foundUser.orElseThrow(() -> new UserNotFoundException("User not found", "-1")).getBalance();
    }

    public ResponseMessage takeMoney(long id, long amountOfWithdraw) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException("User not found", "-1");
        } else {
            if (foundUser.get().getBalance() - amountOfWithdraw >= 0) {
                foundUser.get().setBalance(foundUser.get().getBalance() - amountOfWithdraw);
                userRepository.save(foundUser.get());
                Operation operation = new Operation();
                operation.setOwner(foundUser.get());
                operation.setOperationType(OperationType.WITHDRAW);
                operation.setOperationAmount(amountOfWithdraw);
                operation.setOperationDate(LocalDate.now());
                operationsRepository.save(operation);
            } else {
                throw new InsufficientFundsException("Insufficient funds for withdrawal", "0");
            }
        }
        return new ResponseMessage("Success", "1", LocalDateTime.now());
    }

    public ResponseMessage putMoney(long id, long amountOfDeposit) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException("User not found", "-1");
        } else {
            foundUser.get().setBalance(foundUser.get().getBalance() + amountOfDeposit);
            userRepository.save(foundUser.get());
            Operation operation = new Operation();
            operation.setOwner(foundUser.get());
            operation.setOperationType(OperationType.DEPOSIT);
            operation.setOperationAmount(amountOfDeposit);
            operation.setOperationDate(LocalDate.now());
            operationsRepository.save(operation);
        }
        return new ResponseMessage("Success", "1", LocalDateTime.now());
    }

    public List<Operation> getOperationList(long id, LocalDate startDate, LocalDate finishDate) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException("User not found", "-1");
        } else {
            if (startDate == null || finishDate == null) {
                return foundUser.get().getOperations();
            } else {
                return foundUser.get().getOperations().stream().filter(o ->
                                (o.getOperationDate().isAfter(startDate) || o.getOperationDate().isEqual(startDate)) &&
                                        (o.getOperationDate().isBefore(finishDate) || o.getOperationDate().isEqual(finishDate)))
                        .toList();
            }
        }
    }

    public ResponseMessage transferMoney(long firstUserId, long secondUserId, long amountOfTransfer) {
        Optional<User> firstUser = userRepository.findById(firstUserId);
        Optional<User> secondUser = userRepository.findById(secondUserId);
        if (firstUser.isEmpty() || secondUser.isEmpty()) {
            throw new UserNotFoundException("User not found", "-1");
        } else {
            if (firstUser.get().getBalance() - amountOfTransfer >= 0) {
                firstUser.get().setBalance(firstUser.get().getBalance() - amountOfTransfer);
                secondUser.get().setBalance(secondUser.get().getBalance() + amountOfTransfer);
                userRepository.save(firstUser.get());
                userRepository.save(secondUser.get());

                Operation firstUserOperation = new Operation();
                firstUserOperation.setOwner(firstUser.get());
                firstUserOperation.setOperationType(OperationType.OUTGOING_TRANSFER);
                firstUserOperation.setOperationAmount(amountOfTransfer);
                firstUserOperation.setOperationDate(LocalDate.now());
                operationsRepository.save(firstUserOperation);

                Operation secondUserOperation = new Operation();
                secondUserOperation.setOwner(secondUser.get());
                secondUserOperation.setOperationType(OperationType.INCOMING_TRANSFER);
                secondUserOperation.setOperationAmount(amountOfTransfer);
                secondUserOperation.setOperationDate(LocalDate.now());
                operationsRepository.save(secondUserOperation);
            } else {
                throw new InsufficientFundsException("Insufficient funds for transfer", "0");
            }
        }
        return new ResponseMessage("Success", "1", LocalDateTime.now());
    }
}