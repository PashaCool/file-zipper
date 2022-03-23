package io.pavel.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * The common error handler advice class to provide error messages in standard format.
 */
@ControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleConstraintViolation(IOException ex) {
        return new ResponseEntity<>("Something is wrong, try later", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File too large!");
    }
}
