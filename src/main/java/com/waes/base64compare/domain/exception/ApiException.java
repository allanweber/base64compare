package com.waes.base64compare.domain.exception;

/**
 * Common api exception.
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 7456815743678272554L;

    /**
     * Default constructor receiving explicit message.
     * @param message explicit error message.
     */
    public ApiException(String message) {
        super(message);
    }
}