package com.ibm.demo.exception;


public class ResourceNotFoundException extends BaseServiceException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
