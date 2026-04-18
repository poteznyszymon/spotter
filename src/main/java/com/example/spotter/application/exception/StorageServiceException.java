package com.example.spotter.application.exception;

public class StorageServiceException extends RuntimeException {
    public StorageServiceException(String message, Exception e) {
        super(message, e);
    }
}
