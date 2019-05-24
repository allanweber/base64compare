package com.waes.base64compare.domain.exception;

public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 7456815743678272554L;

    public ApiException(String message) {
        super(message);
    }
}