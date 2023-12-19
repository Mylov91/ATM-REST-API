package ru.mylov.ATM.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mylov.ATM.exceptions.InsufficientFundsException;
import ru.mylov.ATM.utils.ResponseMessage;
import ru.mylov.ATM.exceptions.UserNotFoundException;
import ru.mylov.ATM.models.User;
import ru.mylov.ATM.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        }
        return new ResponseMessage("Success", "1", LocalDateTime.now());
    }
}