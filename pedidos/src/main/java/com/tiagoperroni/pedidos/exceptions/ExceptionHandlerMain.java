package com.tiagoperroni.pedidos.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerMain {
    
    @ExceptionHandler(EstoqueValidationException.class)
    public ResponseEntity<StandardError> validaEstoque(EstoqueValidationException exeption) {
        StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), exeption.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<StandardError> modelNotFound(ModelNotFoundException exeption) {
        StandardError error = new StandardError(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), exeption.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PedidosValidationException.class)
    public ResponseEntity<StandardError> validaPedidos(PedidosValidationException exeption) {
        StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), exeption.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
