package ru.mylov.ATM.models;

public enum OperationType {
    WITHDRAW, DEPOSIT, INCOMING_TRANSFER, OUTGOING_TRANSFER;

    public String getOperationType() {
        return name();
    }
}
