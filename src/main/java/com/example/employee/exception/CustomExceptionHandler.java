package com.example.employee.exception;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.employee.utility.Constants.FAILED_STATUS;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> exceptionHandler(Exception exception) {
        ErrorInfo errorInfo = ErrorInfo.builder()
                .timestamp(LocalDate.now())
                .errorMessage(exception.getMessage())
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(FAILED_STATUS)
                .build();
        return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException exception) {

        List<String> errorMessage = exception.getBindingResult().getAllErrors()
                .stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());

        ErrorInfo errorInfo = ErrorInfo.builder()
                .timestamp(LocalDate.now())
                .errorMessage(errorMessage.toString())
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .status(FAILED_STATUS)
                .build();
        return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EmployeeTableEmptyException.class, EmployeeDoesNotExistException.class})
    public ResponseEntity<ErrorInfo> employeeTableEmptyExceptionHandler(Exception exception) {
        ErrorInfo errorInfo = ErrorInfo.builder()
                .timestamp(LocalDate.now())
                .errorMessage(exception.getMessage())
                .errorCode(HttpStatus.NOT_FOUND.value())
                .status(FAILED_STATUS)
                .build();
        return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AadharAlreadyExistsException.class)
    public ResponseEntity<ErrorInfo> aadharAlreadyExistsExceptionHandler(Exception exception) {
        ErrorInfo errorInfo = ErrorInfo.builder()
                .timestamp(LocalDate.now())
                .errorMessage(exception.getMessage())
                .errorCode(HttpStatus.CONFLICT.value())
                .status(FAILED_STATUS)
                .build();
        return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.CONFLICT);
    }
}
