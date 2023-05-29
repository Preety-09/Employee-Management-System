package com.example.employee.exception;

public class EmployeeDoesNotExistException extends Exception {
    public EmployeeDoesNotExistException(String message) {
        super(message);
    }
}
