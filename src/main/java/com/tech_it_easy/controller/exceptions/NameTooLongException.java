package com.tech_it_easy.controller.exceptions;

public class NameTooLongException extends RuntimeException {
    public NameTooLongException() {
        super("Name too long");
    }

    public NameTooLongException(String message) {
        super(message);
    }
}
