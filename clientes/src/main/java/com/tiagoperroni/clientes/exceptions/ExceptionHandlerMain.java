package com.tiagoperroni.clientes.exceptions;

import java.time.LocalDateTime;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerMain {
    
    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<StandardError> clienteNotFound(ClienteNotFoundException e) {
        var error = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationDataException.class)
    public ResponseEntity<StandardError> clienteNotFound(ValidationDataException e) {
        var error = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> clienteNotFound(ConstraintViolationException e) {
        var error = new StandardError(HttpStatus.BAD_REQUEST.value(), "O formato do e-mail é inválido, verifique.", LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
}
