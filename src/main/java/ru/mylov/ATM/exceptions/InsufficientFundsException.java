package ru.mylov.ATM.exceptions;

public class InsufficientFundsException extends RuntimeException {
    private final String message;
    private final String code;

    public InsufficientFundsException(String message, String code) {
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
