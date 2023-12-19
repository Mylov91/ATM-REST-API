package ru.mylov.ATM.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.mylov.ATM.utils.ResponseMessage;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e) {
        ResponseMessage responseMessage = new ResponseMessage(e.getMessage(), e.getCode(), LocalDateTime.now());
        return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InsufficientFundsException.class)
    public ResponseEntity<Object> handleNotEnoughFundsException(InsufficientFundsException e) {
        ResponseMessage responseMessage = new ResponseMessage(e.getMessage(), e.getCode(), LocalDateTime.now());
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        ResponseMessage responseMessage = new ResponseMessage("Invalid request parameter", "0", LocalDateTime.now());
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }
}
