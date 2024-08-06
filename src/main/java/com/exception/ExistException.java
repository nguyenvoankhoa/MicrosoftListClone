package com.exception;

public class ExistException extends RuntimeException {
    public ExistException() {
        super("Name already exist");
    }
}
