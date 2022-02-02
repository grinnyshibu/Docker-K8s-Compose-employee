package com.qburst.employeedbmanager.exception;

public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

