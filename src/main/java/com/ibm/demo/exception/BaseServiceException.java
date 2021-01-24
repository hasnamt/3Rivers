package com.ibm.demo.exception;


public class BaseServiceException extends RuntimeException {

    private final String error;

    public BaseServiceException(String error) {
        super(error);
        this.error = error;
    }
}
