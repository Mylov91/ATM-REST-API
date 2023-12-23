package ru.mylov.ATM.controllers;

import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mylov.ATM.utils.ResponseMessage;
import ru.mylov.ATM.services.ProcessingService;

import java.time.LocalDate;

@RestController
@Validated
public class MainController {
    private final ProcessingService processingService;

    @Autowired
    public MainController(ProcessingService processingService) {
        this.processingService = processingService;
    }

    @GetMapping("/balance")
    public long showBalance(@RequestParam long id) {
        return processingService.getBalance(id);
    }

    @GetMapping("/withdraw")
    public ResponseMessage takeMoney(@RequestParam long id, @RequestParam @Min(0) long amount) {
        return processingService.takeMoney(id, amount);
    }

    @GetMapping("/deposit")
    public ResponseMessage putMoney(@RequestParam long id, @RequestParam @Min(0) long amount) {
        return processingService.putMoney(id, amount);
    }

    @GetMapping("/operations")
    public String getOperations(@RequestParam long id,
                                @RequestParam(required = false) LocalDate start,
                                @RequestParam(required = false) LocalDate finish) {
        return processingService.getOperationList(id, start, finish).toString();
    }

    @GetMapping("/transfer")
    public ResponseMessage transferMoney(@RequestParam long sender,
                                         @RequestParam long receiver,
                                         @RequestParam long amount) {
        return processingService.transferMoney(sender, receiver, amount);
    }
}
