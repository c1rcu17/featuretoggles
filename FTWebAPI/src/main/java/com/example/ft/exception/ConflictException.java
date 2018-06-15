package com.example.ft.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends ServiceException {

    public ConflictException() {
        this("Conflict Exception");
    }

    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

}
