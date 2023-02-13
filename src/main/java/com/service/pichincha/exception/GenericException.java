package com.service.pichincha.exception;

import org.springframework.http.HttpStatus;

public class GenericException extends RuntimeException {

    private HttpStatus status;

    public GenericException(HttpStatus httpStatus, String message) {
        super(message);
        this.status = httpStatus;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
