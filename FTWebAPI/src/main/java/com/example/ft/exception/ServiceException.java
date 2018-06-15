package com.example.ft.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class ServiceException extends RuntimeException {

    private HttpStatus httpStatus;

    public ServiceException(String message, HttpStatus httpStatus) {
        super(message);
        setHttpStatus(httpStatus);
    }

    public String getHttpReason() {
        return getHttpStatus().getReasonPhrase();
    }

    public Integer getHttpStatusCode() {
        return getHttpStatus().value();
    }

}
