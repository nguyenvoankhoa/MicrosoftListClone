package com.exception;

public class ConstraintViolationException extends RuntimeException {
    public ConstraintViolationException() {
        super("Constraint violation: Data does not meet the required constraints.");
    }
}