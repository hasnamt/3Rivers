package com.ibm.demo.advice;

import com.ibm.demo.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ExcpetionControllerAdvice {

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Requested resource not found")
    @ExceptionHandler(ResourceNotFoundException.class)
    public void handleResourceNotFoundException() {
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "bad request")
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException() {
    }

}
