package com.ing.accm.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
