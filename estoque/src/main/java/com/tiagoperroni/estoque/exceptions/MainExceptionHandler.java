package com.tiagoperroni.estoque.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MainExceptionHandler {

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<StandardError> modelNotFound(ModelNotFoundException modelNotFound) {
        var error = new StandardError(HttpStatus.NOT_FOUND.value(), modelNotFound.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationsException.class)
    public ResponseEntity<StandardError> modelNotFound(ValidationsException validationsException) {
        var error = new StandardError(HttpStatus.BAD_REQUEST.value(), validationsException.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<StandardError> requestToken(MissingRequestHeaderException requestHeaderException) {
        var error = new StandardError(HttpStatus.UNAUTHORIZED.value(), "O token deve ser fornecido.", LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

}
