package com.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Can not find object");
    }
}