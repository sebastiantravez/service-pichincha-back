package com.service.pichincha.exception.handler;

import com.service.pichincha.exception.ExceptionResponse;
import com.service.pichincha.exception.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ExceptionResponse> customExceptionHandler(GenericException exception) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder().code(exception.getStatus().value()).message(exception.getMessage()).build();
        ResponseEntity<ExceptionResponse> response = new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        return response;
    }

}
