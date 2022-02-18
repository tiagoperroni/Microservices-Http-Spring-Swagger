package com.tiagoperroni.clientes.exceptions;

public class ClienteNotFoundException extends RuntimeException {
    
    public ClienteNotFoundException(String msg) {
        super(msg);
    }
}
