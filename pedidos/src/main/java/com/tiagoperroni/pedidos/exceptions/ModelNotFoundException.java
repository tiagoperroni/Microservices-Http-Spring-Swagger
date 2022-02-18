package com.tiagoperroni.pedidos.exceptions;

public class ModelNotFoundException extends RuntimeException{
    
    public ModelNotFoundException(String msg) {
        super(msg);
    }
}
