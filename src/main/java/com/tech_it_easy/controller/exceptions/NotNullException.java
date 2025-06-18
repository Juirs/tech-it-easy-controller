package com.tech_it_easy.controller.exceptions;

public class NotNullException extends RuntimeException {
    public NotNullException() {
        super("Value cannot be null");
    }

    public NotNullException(String message) {
        super(message);
    }
}
