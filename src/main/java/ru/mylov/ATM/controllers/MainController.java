package ru.mylov.ATM.controllers;

import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mylov.ATM.utils.ResponseMessage;
import ru.mylov.ATM.services.UserService;

@RestController
@Validated
public class MainController {
    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/show")
    public long showBalance(@RequestParam long id) {
        return userService.getBalance(id);
    }

    @GetMapping("/take")
    public ResponseMessage takeMoney(@RequestParam long id, @RequestParam @Min(0) long amount) {
        return userService.takeMoney(id, amount);
    }

    @GetMapping("/put")
    public ResponseMessage putMoney(@RequestParam long id, @RequestParam @Min(0) long amount) {
        return userService.putMoney(id, amount);
    }
}
