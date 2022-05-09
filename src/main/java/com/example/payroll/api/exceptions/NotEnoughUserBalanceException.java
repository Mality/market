package com.example.payroll.api.exceptions;

public class NotEnoughUserBalanceException extends RuntimeException {

    public NotEnoughUserBalanceException() {
    }

    public NotEnoughUserBalanceException(String message) {
        super(message);
    }

    public NotEnoughUserBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
