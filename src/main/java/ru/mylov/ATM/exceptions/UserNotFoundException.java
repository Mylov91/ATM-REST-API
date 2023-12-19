package ru.mylov.ATM.exceptions;

import org.springframework.beans.factory.annotation.Autowired;

public class UserNotFoundException extends RuntimeException{
    private final String message;
    private final String code;

    @Autowired
    public UserNotFoundException(String message, String code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}

