package com.example.employee.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public ResponseEntity<Object> generateResponse(String message, HttpStatus httpStatusCode) {
        Map<String, Object> successMap = new HashMap<>();
        successMap.put("message", message);
        successMap.put("code", httpStatusCode.value());

        return new ResponseEntity<>(successMap, httpStatusCode);
    }
}
