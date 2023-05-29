package com.example.employee.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class ErrorInfo {
    private LocalDate timestamp;
    private String errorMessage;
    private Integer errorCode;
    private String status;
}
