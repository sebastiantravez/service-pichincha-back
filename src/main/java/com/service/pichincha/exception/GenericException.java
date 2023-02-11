package com.service.pichincha.exception;

import org.springframework.http.HttpStatus;

public class GenericException extends RuntimeException {

    private HttpStatus status;

    public GenericException(String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
