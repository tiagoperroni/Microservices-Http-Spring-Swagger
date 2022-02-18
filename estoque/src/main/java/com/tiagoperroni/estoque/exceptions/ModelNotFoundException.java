package com.tiagoperroni.estoque.exceptions;

public class ModelNotFoundException extends RuntimeException {
    
    public ModelNotFoundException(String msg) {
        super(msg);
    }

}
