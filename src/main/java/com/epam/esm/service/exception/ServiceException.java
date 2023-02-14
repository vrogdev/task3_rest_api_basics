package com.epam.esm.service.exception;

public class ServiceException extends Exception {
    public ServiceException(String messageCode) {
        super(messageCode);
    }
}