package com.example.ft.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ServiceException {

    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException() {
        this("Unauthorized");
    }

}
