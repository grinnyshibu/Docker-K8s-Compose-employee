package com.qburst.employeeproxy.exception;

public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

