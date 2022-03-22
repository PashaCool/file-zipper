package io.pavel.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The common error handler advice class to provide error messages in standard format.
 */
@ControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleConstraintViolation(IOException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
