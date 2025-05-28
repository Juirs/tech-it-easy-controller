package com.tech_it_easy.controller.exceptions;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException() {
        super("Record not found.");
    }

    public RecordNotFoundException(String message) {
        super(message);
    }
}
