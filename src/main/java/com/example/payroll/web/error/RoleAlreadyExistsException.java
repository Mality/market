package com.example.payroll.web.error;

public class RoleAlreadyExistsException extends RuntimeException {

    public RoleAlreadyExistsException() {
        super();
    }

    public RoleAlreadyExistsException(String message) {
        super(message);
    }
}
